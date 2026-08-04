package dev.tymoshenko.a6amcafe

import androidx.compose.ui.window.ComposeUIViewController
import dev.tymoshenko.a6amcafe.share.ShareObj
import dev.tymoshenko.a6amcafe.share.ShareProvider

fun MainViewController(shareProvider: ShareProvider) = ComposeUIViewController {
    with(shareProvider) {
        ShareObj.setSvgHelper(this.getSvgHelper())
    }

    App()
}