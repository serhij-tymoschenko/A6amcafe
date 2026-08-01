package dev.tymoshenko.a6amcafe.app.images

import androidx.compose.ui.graphics.ImageBitmap

interface SvgProvider {
    fun getSvg(imageUrl: String, onReady: (ImageBitmap) -> Unit)
}