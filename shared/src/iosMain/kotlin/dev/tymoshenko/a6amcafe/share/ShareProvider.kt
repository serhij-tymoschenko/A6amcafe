package dev.tymoshenko.a6amcafe.share

import dev.tymoshenko.a6amcafe.utils.images.SvgProvider

interface ShareProvider {
    fun getSvgHelper(): SvgProvider
}