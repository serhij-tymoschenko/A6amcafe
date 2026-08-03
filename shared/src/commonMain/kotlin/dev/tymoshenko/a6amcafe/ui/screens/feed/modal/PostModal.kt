package dev.tymoshenko.a6amcafe.ui.screens.feed.modal

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostModal(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    scope: CoroutineScope
) {
    ModalBottomSheet(
        modifier = modifier
            .fillMaxWidth(),
        onDismissRequest = onDismiss,
        sheetGesturesEnabled = false
    ) {

    }
}