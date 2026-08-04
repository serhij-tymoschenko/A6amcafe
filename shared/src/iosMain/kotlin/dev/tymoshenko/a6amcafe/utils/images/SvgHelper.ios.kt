package dev.tymoshenko.a6amcafe.utils.images

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import dev.tymoshenko.a6amcafe.data.models.mashi.colors.SelectedColors
import dev.tymoshenko.a6amcafe.share.ShareObj
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import org.jetbrains.skia.Image
import platform.UIKit.UIImage
import platform.UIKit.UIImagePNGRepresentation
import platform.posix.memcpy

actual class SvgHelper : SvgProvider {

    override fun getSvg(imageUrl: String, colors: SelectedColors, onReady: (ImageBitmap?) -> Unit) {
        ShareObj.svgHelper.getSvg(
            imageUrl = imageUrl,
            colors = colors,
            onReady = onReady
        )
    }
}

class SvgConverter {
    @OptIn(ExperimentalForeignApi::class)
    fun toComposeImageBitmap(image: UIImage): ImageBitmap? {
        val nsData = UIImagePNGRepresentation(image) ?: return null
        if (nsData.length.toInt() == 0) return null

        val bytes = ByteArray(nsData.length.toInt())
        bytes.usePinned { pinned ->
            memcpy(pinned.addressOf(0), nsData.bytes, nsData.length)
        }

        return Image.makeFromEncoded(bytes).toComposeImageBitmap()
    }
}

