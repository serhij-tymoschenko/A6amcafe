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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.tymoshenko.a6amcafe.ui.screens.feed.composables.Mashi
import dev.tymoshenko.a6amcafe.ui.theme.greyBackground
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MashiesFeed() {
    val vm = koinViewModel<MashiesFeedViewModel>()

    Box(modifier = Modifier.fillMaxSize()
        .background(greyBackground))

    BoxWithConstraints {
        val itemWidthDp = (maxWidth - 16.dp) / 2 - 8.dp

        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalItemSpacing = 8.dp,
            contentPadding = PaddingValues(0.dp),
        ) {
            itemsIndexed(items = listOf(1, 2, 3, 4, 5, 7, 56346,346346,346534,3,2,3,3,3,3,3,3,3,3)) { index, item ->
                // Apply top padding only to the very first left item
                val topPadding = if (index == 1) 96.dp else 0.dp

                Mashi(modifier = Modifier.padding(top = topPadding).width(itemWidthDp))
            }
        }
    }



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