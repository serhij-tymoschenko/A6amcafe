package dev.tymoshenko.a6amcafe.ui.screens.feed.modal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.room.util.TableInfo
import androidx.savedstate.savedState
import coil3.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostModal(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    state: SheetState,
    scope: CoroutineScope
) {
    ModalBottomSheet(
        modifier = modifier
            .fillMaxWidth(),
        sheetState = state,
        onDismissRequest = onDismiss,
        sheetGesturesEnabled = false,
        dragHandle = { },
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp),
        ) {
            Column {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8)),
                    model = "https://ipfs.filebase.io/ipfs/QmNs1kNe7nG4dJXQStqZpV1W8sE8rFgsmGv9hfFP8kEsTe",
                    contentDescription = null,
                )

                Text("FF")
            }

        }
    }
}