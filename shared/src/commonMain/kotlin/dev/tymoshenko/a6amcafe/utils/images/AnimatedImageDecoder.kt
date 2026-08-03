package fr.outadoc.justchatting.utils.coil

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import coil3.Canvas
import coil3.Image
import coil3.ImageLoader
import coil3.asImage
import coil3.decode.DecodeResult
import coil3.decode.Decoder
import coil3.decode.ImageSource
import coil3.fetch.SourceFetchResult
import coil3.request.Options
import okio.BufferedSource
import okio.ByteString.Companion.encodeUtf8
import okio.use
import org.jetbrains.skia.AnimationFrameInfo
import org.jetbrains.skia.Codec
import org.jetbrains.skia.Data
import org.jetbrains.skia.ImageInfo
import kotlin.time.TimeSource
import org.jetbrains.skia.Bitmap as SkiaBitmap
import org.jetbrains.skia.Canvas as SkiaCanvas
import org.jetbrains.skia.Image as SkiaImage

@Deprecated("Replace with proper coil3 implementation once available")
internal class AnimatedImageDecoder(
    private val source: ImageSource,
    private val prerenderFrames: Boolean = false,
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
            if (!isGif(result.source.source())) return null
            return AnimatedImageDecoder(
                source = result.source,
                prerenderFrames = prerenderFrames
            )
        }
    }
}

private class AnimatedSkiaImage(
    private val codec: Codec,
    prerenderFrames: Boolean,
) : Image, AutoCloseable {

    private val info: ImageInfo = codec.imageInfo

    override val width: Int get() = codec.width
    override val height: Int get() = codec.height
    override val shareable: Boolean get() = false

    // Cache decoded SkiaBitmaps directly
    private val cachedBitmaps = Array<SkiaBitmap?>(codec.frameCount) { index ->
        if (prerenderFrames) decodeFrame(index) else null
    }

    private var invalidateTick by mutableIntStateOf(0)

    private var currentRepetitionStartTime: TimeSource.Monotonic.ValueTimeMark? = null
    private var currentRepetitionCount = 0
    private var lastDrawnFrameIndex = 0
    private var isAnimationComplete = false

    override val size: Long
        get() {
            val baseSize = info.computeMinByteSize().toLong()
            val pixelByteSize = if (baseSize <= 0L) 4L * width * height else baseSize
            val frameCountMultiplier = cachedBitmaps.count { it != null }.coerceAtLeast(1)
            return (pixelByteSize * frameCountMultiplier).coerceAtLeast(0L)
        }

    override fun draw(canvas: Canvas) {
        if (codec.frameCount == 0) return

        @Suppress("UNUSED_VARIABLE")
        val tick = invalidateTick

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

        var accumulatedDuration = 0
        var frameIndexToDraw = codec.frameCount - 1

        val framesInfo = codec.framesInfo
        for (i in framesInfo.indices) {
            val frameDuration = framesInfo[i].safeFrameDuration
            if (accumulatedDuration + frameDuration > elapsedTime) {
                frameIndexToDraw = i
                break
            }
            accumulatedDuration += frameDuration
        }

        lastDrawnFrameIndex = frameIndexToDraw
        canvas.drawFrame(frameIndexToDraw)

        val totalLoopDuration = framesInfo.sumOf { it.safeFrameDuration.toLong() }
        val isLastFrame = frameIndexToDraw == codec.frameCount - 1

        if (isLastFrame && elapsedTime >= totalLoopDuration) {
            currentRepetitionCount++
            val maxRepetitions = codec.repetitionCount
            if (maxRepetitions in 1..currentRepetitionCount) {
                isAnimationComplete = true
            } else {
                currentRepetitionStartTime = TimeSource.Monotonic.markNow()
            }
        }

        if (!isAnimationComplete) {
            invalidateTick++
        }
    }

    private fun decodeFrame(frameIndex: Int): SkiaBitmap {
        val bitmap = SkiaBitmap().apply { allocPixels(info) }

        val requiredFrame = codec.framesInfo.getOrNull(frameIndex)?.requiredFrame
        if (requiredFrame != null && requiredFrame != -1) {
            val priorBitmap = getOrDecodeFrame(requiredFrame)
            val priorImage = SkiaImage.makeFromBitmap(priorBitmap)
            val canvas = SkiaCanvas(bitmap)
            canvas.drawImage(priorImage, 0f, 0f)
            priorImage.close()
        }

        codec.readPixels(bitmap, frameIndex)
        return bitmap
    }

    private fun getOrDecodeFrame(frameIndex: Int): SkiaBitmap {
        return cachedBitmaps[frameIndex] ?: decodeFrame(frameIndex).also {
            cachedBitmaps[frameIndex] = it
        }
    }

    private fun Canvas.drawFrame(frameIndex: Int) {
        val skiaBitmap = getOrDecodeFrame(frameIndex)

        // Convert Skia Bitmap into a platform-compatible Coil Bitmap/Image
        // On Desktop/Skiko platforms, coil3.Bitmap is org.jetbrains.skia.Bitmap
        val coilImage = (skiaBitmap as coil3.Bitmap).asImage(shareable = false)

        // Delegate drawing to Coil's BitmapImage implementation
        coilImage.draw(this)
    }

    override fun close() {
        cachedBitmaps.forEach { it?.close() }
        codec.close()
    }
}

private val AnimationFrameInfo.safeFrameDuration: Int
    get() = if (duration <= 0) DEFAULT_FRAME_DURATION else duration

private const val DEFAULT_FRAME_DURATION = 100

private val GIF_HEADER_87A = "GIF87a".encodeUtf8()
private val GIF_HEADER_89A = "GIF89a".encodeUtf8()

private fun isGif(source: BufferedSource): Boolean {
    return source.rangeEquals(0, GIF_HEADER_89A) ||
            source.rangeEquals(0, GIF_HEADER_87A)
}