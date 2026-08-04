package dev.tymoshenko.a6amcafe.share

object ShareObj {
    lateinit var svgHelper: SvgIosProvider
        private set

    fun setSvgHelper(provider: SvgIosProvider) {
        svgHelper = provider
    }
}