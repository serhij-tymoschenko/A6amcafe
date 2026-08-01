package dev.tymoshenko.a6amcafe.app.images

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import org.jetbrains.skia.Image
import platform.UIKit.UIImage
import platform.UIKit.UIImagePNGRepresentation
import platform.posix.memcpy

actual class SvgHelper : SvgProvider {
    override fun getSvg(imageUrl: String, onReady: (ImageBitmap) -> Unit) {

    }
}

@OptIn(ExperimentalForeignApi::class)
fun UIImage.toComposeImageBitmap(): ImageBitmap? {
    val nsData = UIImagePNGRepresentation(this) ?: return null
    if (nsData.length.toInt() == 0) return null

    val bytes = ByteArray(nsData.length.toInt())
    bytes.usePinned { pinned ->
        memcpy(pinned.addressOf(0), nsData.bytes, nsData.length)
    }

    return Image.makeFromEncoded(bytes).toComposeImageBitmap()
}