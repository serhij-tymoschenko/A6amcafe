package dev.tymoshenko.a6amcafe.ui.screens.feed.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.TableInfo
import coil3.compose.AsyncImage
import dev.tymoshenko.a6amcafe.ui.theme.creamBackground

@Composable
fun Mashi(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(9))
            .background(creamBackground)
            .padding(4.dp)
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(552 / 736F)
                    .clip(RoundedCornerShape(8)),
                model = "https://ipfs.filebase.io/ipfs/QmNs1kNe7nG4dJXQStqZpV1W8sE8rFgsmGv9hfFP8kEsTe",
                contentDescription = null
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Username | Unique mashi #111", color = Color.Black.copy(0.7F), fontSize = 10.sp)
            }
        }
    }
}

@Preview
@Composable
fun MashiPreview() {
    Mashi()
}