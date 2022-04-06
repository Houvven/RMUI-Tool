package h.w.rmuitool.ui.tool

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.Flow

@Composable
fun Test(flow: Flow<PagingData<String>>) {
    
    val lazyPagingItems = flow.collectAsLazyPagingItems()
    LazyColumn {

    }
}