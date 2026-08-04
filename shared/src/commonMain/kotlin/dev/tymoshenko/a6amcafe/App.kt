package dev.tymoshenko.a6amcafe

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.tymoshenko.a6amcafe.ui.screens.feed.MashiesFeed

@Composable
@Preview
fun App() {
    MaterialTheme {
        MashiesFeed()
    }
}