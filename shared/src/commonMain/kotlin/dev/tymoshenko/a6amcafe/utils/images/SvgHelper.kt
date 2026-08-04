package dev.tymoshenko.a6amcafe.utils.images

import androidx.compose.ui.graphics.ImageBitmap
import dev.tymoshenko.a6amcafe.data.models.mashi.colors.SelectedColors

expect class SvgHelper() : SvgProvider {
    override fun getSvg(
        imageUrl: String,
        colors: SelectedColors,
        onReady: (ImageBitmap?) -> Unit
    )
}