package dev.tymoshenko.a6amcafe.share

import dev.tymoshenko.a6amcafe.data.models.mashi.colors.SelectedColors
import platform.UIKit.UIImage

interface SvgIosProvider {
    fun getSvg(imageUrl: String, colors: SelectedColors, onReady: (UIImage?) -> Unit)
}