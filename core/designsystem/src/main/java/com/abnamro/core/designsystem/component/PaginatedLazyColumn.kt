package com.abnamro.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(FlowPreview::class)
@Composable
fun <T> PaginatedLazyColumn(
    modifier: Modifier = Modifier,
    items: List<T>,
    loadMoreItems: () -> Unit,
    listState: LazyListState,
    isLoading: Boolean,
    buffer: Int = 3,
    itemContent: @Composable (T) -> Unit,
    loadingContent: @Composable () -> Unit = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val totalItemsCount = listState.layoutInfo.totalItemsCount
            val lastVisibleItemIndex =
                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItemIndex >= (totalItemsCount - buffer)
        }
    }

    LaunchedEffect(listState, isLoading) {
        snapshotFlow { shouldLoadMore.value }
            .debounce(300)
            .distinctUntilChanged()
            .collect { shouldLoad ->
                if (shouldLoad && !isLoading) {
                    loadMoreItems()
                }
            }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        state = listState
    ) {
        items(items) { item ->
            itemContent(item)
        }

        if (isLoading) {
            item {
                loadingContent()
            }
        }
    }
}