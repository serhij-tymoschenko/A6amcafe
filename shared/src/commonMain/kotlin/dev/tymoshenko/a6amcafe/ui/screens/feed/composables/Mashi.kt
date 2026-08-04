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
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import dev.tymoshenko.a6amcafe.data.models.mashi.colors.SelectedColors
import dev.tymoshenko.a6amcafe.ui.theme.creamBackground
import dev.tymoshenko.a6amcafe.utils.images.SvgHelper
import dev.tymoshenko.a6amcafe.utils.platform.PlatformType
import dev.tymoshenko.a6amcafe.utils.images.AnimatedSkiaImageDecoder
import org.koin.compose.koinInject

@Composable
fun Mashi(
    modifier: Modifier = Modifier
) {
    val platformType = koinInject<PlatformType>()
    val ctx = LocalPlatformContext.current

    var model by remember {
        mutableStateOf<ImageBitmap?>(null)
    }

    val req = when (platformType) {
        PlatformType.IOS -> ImageRequest.Builder(ctx)
            .data("https://katzemon.com/api/apng/QmVDo7RbmGZy8wXBwKdtYy2cFUkGq1FXqD1SPdqF5bv4rt")
            .decoderFactory(AnimatedSkiaImageDecoder.Factory())
            .build()

        else -> ImageRequest.Builder(ctx)
            .data("https://round-peach-hippopotamus.myfilebase.com/ipfs/QmVDo7RbmGZy8wXBwKdtYy2cFUkGq1FXqD1SPdqF5bv4rt")
            .build()
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(552 / 736F)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(552 / 736F),
                    model = req,
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