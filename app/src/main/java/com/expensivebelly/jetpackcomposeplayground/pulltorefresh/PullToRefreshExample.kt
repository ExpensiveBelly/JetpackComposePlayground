package com.expensivebelly.jetpackcomposeplayground.pulltorefresh

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshScreenExample(modifier: Modifier) {
    var items by remember { mutableStateOf(List(20) { "Item $it" }) }
    val scope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }

    // Simulate a network request
    val refreshData = suspend {
        isRefreshing = true
        delay(1000) // Simulate loading for 2 seconds
        val newItems = List(20) { "Refreshed Item $it" }
        items = newItems
        isRefreshing = false
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            scope.launch {
                refreshData()
            }
        },
        modifier = modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(items) { item ->
                Text(
                    text = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.White)
                        .padding(8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}