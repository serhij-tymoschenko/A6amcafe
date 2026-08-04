package dev.tymoshenko.a6amcafe.utils.images

import androidx.compose.ui.graphics.ImageBitmap
import dev.tymoshenko.a6amcafe.data.models.mashi.colors.SelectedColors

interface SvgProvider {
    fun getSvg(imageUrl: String, colors: SelectedColors, onReady: (ImageBitmap?) -> Unit)
}