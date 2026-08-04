package dev.tymoshenko.a6amcafe.share

import dev.tymoshenko.a6amcafe.utils.images.SvgProvider

object ShareObj {
    lateinit var svgHelper: SvgProvider
        private set

    fun setSvgHelper(provider: SvgProvider) {
        svgHelper = provider
    }
}