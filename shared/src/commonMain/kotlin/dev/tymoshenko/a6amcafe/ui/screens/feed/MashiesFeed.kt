package dev.tymoshenko.a6amcafe.ui.screens.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.tymoshenko.a6amcafe.ui.screens.feed.composables.FeedGrid
import dev.tymoshenko.a6amcafe.ui.screens.feed.composables.Mashi
import dev.tymoshenko.a6amcafe.ui.screens.feed.modal.PostModal
import dev.tymoshenko.a6amcafe.ui.theme.greyBackground
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MashiesFeed() {
    val vm = koinViewModel<MashiesFeedViewModel>()
    val modalState = rememberModalBottomSheetState(true)
    val scope = rememberCoroutineScope()

//    LaunchedEffect(Unit) {
//        modalState.show()
//    }

    Box(modifier = Modifier.fillMaxSize()
        .background(greyBackground))

    Box {
        FeedGrid()
    }

//    PostModal(
//        modifier = Modifier,
//        state = modalState,
//        onDismiss = {},
//        scope = scope
//    )


//    LazyColumn {
//        item {
//            Mashi(modifier = Modifier.fillMaxWidth(0.9f).padding(start = 16.dp, end = 8.dp))
//            Mashi(modifier = Modifier.fillMaxWidth(0.9f).padding(start = 16.dp, end = 8.dp))
//            Mashi(modifier = Modifier.fillMaxWidth(0.9f).padding(start = 16.dp, end = 8.dp))
//            Mashi(modifier = Modifier.fillMaxWidth(0.9f).padding(start = 16.dp, end = 8.dp))
//            Mashi(modifier = Modifier.fillMaxWidth(0.9f).padding(start = 16.dp, end = 8.dp))
//
//        }
//    }

}

@Preview
@Composable
fun MashiesFeedPreview() {
    MashiesFeed()
}