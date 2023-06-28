package com.mkchtv.cleantemplate.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
fun ElementListScreen(
    viewModel: ElementsListViewModel,
    onListItemClick: (item: ElementItem) -> Unit = {},
    onAddNewElementClick: () -> Unit = {},
) {
    val elementsState = viewModel.elementsState.collectAsState()
    val lazyColumnState = rememberLazyListState()
    val isFabVisible by remember {
        derivedStateOf {
            lazyColumnState.firstVisibleItemIndex == 0
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = lazyColumnState,
            contentPadding = PaddingValues(16.dp)
        ) {
            items(
                items = elementsState.value,
                key = { element -> element.id }
            ) { element ->
                ElementColumnItem(
                    element,
                    onListItemClick = onListItemClick
                )
                Divider(thickness = 8.dp, color = Color.Transparent)
            }
        }
        AddNewElementButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp, end = 16.dp),
            isVisible = isFabVisible,
            onClick = onAddNewElementClick
        )
    }
}

@Composable
private fun ElementColumnItem(
    item: ElementItem,
    onListItemClick: (item: ElementItem) -> Unit = {},
) {
    ListItem(
        overlineContent = { Text(text = item.name) },
        headlineContent = { Text(text = item.description) },
        shadowElevation = 1.0.dp,
        modifier = Modifier.clickable { onListItemClick(item) }
    )
}

@ExperimentalAnimationApi
@Composable
private fun AddNewElementButton(
    modifier: Modifier,
    isVisible: Boolean,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = scaleIn(),
        exit = scaleOut(),
    ) {
        FloatingActionButton(
            onClick = onClick
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add new element")
        }
    }
}
