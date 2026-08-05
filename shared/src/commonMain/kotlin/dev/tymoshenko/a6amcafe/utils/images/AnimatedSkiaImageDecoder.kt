package dev.tymoshenko.a6amcafe.utils.images

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import coil3.Canvas
import coil3.Image
import coil3.ImageLoader
import coil3.decode.DecodeResult
import coil3.decode.Decoder
import coil3.decode.ImageSource
import coil3.fetch.SourceFetchResult
import coil3.request.Options
import okio.BufferedSource
import okio.ByteString.Companion.encodeUtf8
import okio.ByteString.Companion.toByteString
import okio.use
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Codec
import org.jetbrains.skia.ColorAlphaType
import org.jetbrains.skia.ColorInfo
import org.jetbrains.skia.ColorSpace
import org.jetbrains.skia.ColorType
import org.jetbrains.skia.Data
import org.jetbrains.skia.ImageInfo
import kotlin.time.TimeSource
import org.jetbrains.skia.Image as SkiaImage

@Deprecated("Replace with proper coil3 implementation once available")
internal class AnimatedSkiaImageDecoder(
    private val source: ImageSource,
    private val prerenderFrames: Boolean = true,
) : Decoder {

    override suspend fun decode(): DecodeResult {
        val bytes = source.source().use { it.readByteArray() }
        val codec = Codec.makeFromData(Data.makeFromBytes(bytes))
        return DecodeResult(
            image = AnimatedSkiaImage(codec, prerenderFrames),
            isSampled = false,
        )
    }

    class Factory(
        private val prerenderFrames: Boolean = false,
    ) : Decoder.Factory {

        override fun create(
            result: SourceFetchResult,
            options: Options,
            imageLoader: ImageLoader,
        ): Decoder? {
            if (!isSupportedFormat(result.source.source())) return null
            return AnimatedSkiaImageDecoder(
                source = result.source,
                prerenderFrames = prerenderFrames
            )
        }
    }
}

/**
 * Above this many decoded bytes we refuse to eagerly pre-render every frame,
 * even if the caller asked for it. iOS does not have a generous JVM-style heap:
 * a large GIF/APNG fully decoded frame-by-frame can spike RSS enough to trigger
 * a jetsam kill. Lazily decoding + caching on first use keeps peak memory bounded
 * by "frames actually shown" instead of "frames that exist".
 */
private const val MAX_EAGER_PRERENDER_BYTES = 24L * 1024 * 1024 // 24 MB

private class AnimatedSkiaImage(
    private val codec: Codec,
    prerenderFrames: Boolean,
) : Image {
    private val imageInfo = ImageInfo(
        colorInfo = ColorInfo(
            // BGRA_8888 is Metal's native little-endian surface format on iOS, so this
            // frame buffer can be uploaded/drawn without an extra swizzle - keep as-is.
            colorType = ColorType.BGRA_8888,
            alphaType = ColorAlphaType.UNPREMUL,
            colorSpace = ColorSpace.sRGB,
        ),
        width = codec.width,
        height = codec.height,
    )

    // Single reusable scratch bitmap for decoding - avoids allocating a native
    // pixel buffer per frame. Its contents are copied out (see decodeFrame)
    // before being reused, so it's safe to overwrite on every frame.
    private val scratchBitmap = Bitmap().apply { allocPixels(codec.imageInfo) }

    // Cache fully-built Skia Images (not raw bytes). Building an Image from raw
    // bytes via makeRaster() is itself a real cost (native alloc + potential
    // texture upload on first draw). Without this cache, draw() rebuilt a new
    // Image every single call - including every frame while paused/frozen on
    // the last frame - which is wasted native work on every composition pass.
    private val frameCache = arrayOfNulls<SkiaImage>(codec.frameCount)

    // Precompute frame durations once as a primitive IntArray + prefix-sum table.
    // The old implementation walked codec.framesInfo (a boxed list of
    // AnimationFrameInfo, iterated via withIndex()) inside draw() on every single
    // frame of every animation - i.e. on every vsync while animating. That's an
    // iterator + IndexedValue allocation per redraw. Precomputing once removes
    // all per-draw allocation from the hot loop.
    private val frameDurations = IntArray(codec.frameCount) { i ->
        codec.framesInfo[i].duration.let { if (it <= 0) DEFAULT_FRAME_DURATION else it }
    }
    private val cumulativeDurations = IntArray(codec.frameCount).also { cum ->
        var acc = 0
        for (i in frameDurations.indices) {
            cum[i] = acc
            acc += frameDurations[i]
        }
    }
    private val totalDuration = frameDurations.sum()

    init {
        val estimatedFullSize = imageInfo.computeMinByteSize().toLong() * codec.frameCount
        if (prerenderFrames && estimatedFullSize in 1..MAX_EAGER_PRERENDER_BYTES) {
            for (index in 0 until codec.frameCount) {
                frameCache[index] = decodeFrame(index)
            }
        }
        // else: fall back to lazy decode-on-first-draw, capped by MAX_EAGER_PRERENDER_BYTES.
    }

    private var invalidateTick by mutableIntStateOf(0)

    private var currentRepetitionStartTime: TimeSource.Monotonic.ValueTimeMark? = null
    private var currentRepetitionCount = 0
    private var lastDrawnFrameIndex = 0
    private var isAnimationComplete = false

    override val size: Long
        get() {
            var size = codec.imageInfo.computeMinByteSize().toLong()
            if (size <= 0L) {
                size = 4L * codec.width * codec.height
            }
            return size.coerceAtLeast(0)
        }

    override val width: Int get() = codec.width
    override val height: Int get() = codec.height
    override val shareable: Boolean get() = false

    override fun draw(canvas: Canvas) {
        if (codec.frameCount == 0) return

        if (codec.frameCount == 1) {
            canvas.drawFrame(0)
            return
        }

        if (isAnimationComplete) {
            canvas.drawFrame(lastDrawnFrameIndex)
            return
        }

        val startTime = currentRepetitionStartTime
            ?: TimeSource.Monotonic.markNow().also { currentRepetitionStartTime = it }
        val elapsedTime = startTime.elapsedNow().inWholeMilliseconds

        // Binary-free linear scan over a primitive IntArray - no boxing, no
        // iterator object, cache-friendly. (Frame counts here are small enough
        // that a linear scan beats the complexity of a binary search; the point
        // is avoiding allocation, not algorithmic complexity.)
        var frameIndexToDraw = codec.frameCount - 1
        for (index in cumulativeDurations.indices) {
            if (cumulativeDurations[index] > elapsedTime) {
                frameIndexToDraw = (index - 1).coerceAtLeast(0)
                break
            }
        }

        lastDrawnFrameIndex = frameIndexToDraw

        isAnimationComplete = codec.repetitionCount in 1..currentRepetitionCount &&
                frameIndexToDraw == (codec.frameCount - 1)

        canvas.drawFrame(frameIndexToDraw)

        val drewLastFrame = frameIndexToDraw == codec.frameCount - 1
        val hasLastFrameDurationElapsed = elapsedTime >= totalDuration

        if (!isAnimationComplete && drewLastFrame && hasLastFrameDurationElapsed) {
            lastDrawnFrameIndex = 0
            currentRepetitionCount++
            currentRepetitionStartTime = null
        }

        if (!isAnimationComplete) {
            invalidateTick++
        }
    }

    /** Decodes [frameIndex] into the shared scratch bitmap, copies the pixels out
     *  into an independent buffer, and builds a standalone [SkiaImage] from that
     *  copy. The copy is required because scratchBitmap is reused for every
     *  frame - but we now build the SkiaImage exactly once per frame and cache
     *  it, rather than rebuilding it on every draw() call. */
    private fun decodeFrame(frameIndex: Int): SkiaImage {
        codec.readPixels(scratchBitmap, frameIndex)
        val bytes = scratchBitmap.readPixels(imageInfo, imageInfo.minRowBytes)!!
        return SkiaImage.makeRaster(
            imageInfo = imageInfo,
            bytes = bytes,
            rowBytes = imageInfo.minRowBytes,
        )
    }

    private fun Canvas.drawFrame(frameIndex: Int) {
        val image = frameCache[frameIndex] ?: decodeFrame(frameIndex).also { frameCache[frameIndex] = it }
        drawImage(image = image, left = 0f, top = 0f)
    }
}

// GIF Headers
private val GIF_HEADER_87A = "GIF87a".encodeUtf8()
private val GIF_HEADER_89A = "GIF89a".encodeUtf8()

// WebP Headers ("RIFFxxxxWEBP")
private val RIFF_HEADER = "RIFF".encodeUtf8()
private val WEBP_HEADER = "WEBP".encodeUtf8()

// PNG Magic Bytes
private val PNG_HEADER = byteArrayOf(
    0x89.toByte(), 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A
).toByteString()

private const val DEFAULT_FRAME_DURATION = 100

private fun isSupportedFormat(source: BufferedSource): Boolean {
    val peek = source.peek()
    return isGif(peek) || isWebP(peek) || isPng(peek)
}

private fun isGif(source: BufferedSource): Boolean {
    return source.rangeEquals(0, GIF_HEADER_89A) ||
            source.rangeEquals(0, GIF_HEADER_87A)
}

private fun isWebP(source: BufferedSource): Boolean {
    return source.rangeEquals(0, RIFF_HEADER) &&
            source.rangeEquals(8, WEBP_HEADER)
}

private fun isPng(source: BufferedSource): Boolean {
    return source.rangeEquals(0, PNG_HEADER)
}