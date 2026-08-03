package dev.tymoshenko.a6amcafe.ui.screens.feed.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FeedGrid(
    modifier: Modifier = Modifier,
    items: List<Int> = listOf(1, 2, 3, 4, 5, 7, 56346,346346,346534,3,2,3,3,3,3,3,3,3,3), // TODO: Replace
) {
    LazyVerticalStaggeredGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalItemSpacing = 8.dp,
        contentPadding = PaddingValues(0.dp),
    ) {
        itemsIndexed(items = items) { index, item ->
            // Apply top padding only to the very first left item
            val topPadding = if (index == 1) 96.dp else 0.dp

            Mashi(
                modifier = Modifier
                    .padding(top = topPadding)
                    .fillMaxWidth()
            )
        }
    }
}