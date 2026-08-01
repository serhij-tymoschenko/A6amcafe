package dev.tymoshenko.a6amcafe.ui.screens.auth.composables

import a6amcafe.shared.generated.resources.Res
import a6amcafe.shared.generated.resources.katze
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource

@Composable
fun LogoSection(modifier: Modifier) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxHeight()
                .aspectRatio(1F),
            contentAlignment = Alignment.BottomEnd
        ) {
            Image(
                modifier = Modifier.size(96.dp),
                painter = painterResource(Res.drawable.katze),
                contentDescription = null
            )
        }
    }
}