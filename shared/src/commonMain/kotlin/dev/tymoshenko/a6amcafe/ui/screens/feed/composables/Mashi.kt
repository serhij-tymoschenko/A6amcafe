package dev.tymoshenko.a6amcafe.ui.screens.feed.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import dev.tymoshenko.a6amcafe.data.models.mashi.colors.SelectedColors
import dev.tymoshenko.a6amcafe.ui.theme.creamBackground
import dev.tymoshenko.a6amcafe.utils.images.SvgHelper

@Composable
fun Mashi(
    modifier: Modifier = Modifier
) {
    var model by remember {
        mutableStateOf<ImageBitmap?>(null)
    }

    LaunchedEffect(Unit) {
        SvgHelper().getSvg(
            "https://katzemon.com/api/svg/QmSaxV6KT8jTFu5xxwFbxTrQpfoAhsqqMTteTmHCwJUhrE",
            SelectedColors("#0000FF", "#0000FF", "#0000FF"),
        ) { bitmap -> model = bitmap }
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(9))
            .background(creamBackground)
            .padding(4.dp)
    ) {
        Column {
            Box {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(552 / 736F),
                    model = "https://round-peach-hippopotamus.myfilebase.com/ipfs/QmPCaPuKA4Q6JZ96B2jnK58Y8rfz1Hdkv4G4ovcR5s8Hko",
                    contentDescription = null
                )

                model?.let {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8)),
                        bitmap = it,
                        contentDescription = null
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Username | Unique mashi #111",
                    color = Color.Black.copy(0.7F),
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun MashiPreview() {
    Mashi()
}