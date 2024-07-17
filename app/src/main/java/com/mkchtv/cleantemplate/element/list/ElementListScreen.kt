package com.mkchtv.cleantemplate.element.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mkchtv.cleantemplate.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
fun ElementListScreen(
    elementList: List<ElementItem>,
    onElementClick: (item: ElementItem) -> Unit,
    onAddNewElementClick: () -> Unit,
    onPullNewElementRequested: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.elements)) },
                actions = {
                    IconButton(onClick = onPullNewElementRequested) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_pull),
                            contentDescription = stringResource(id = R.string.cd_pull_new_element),
                        )
                    }
                }
            )
        },
    ) {
        val modifier = Modifier
            .fillMaxSize()
            .padding(
                top = it.calculateTopPadding(),
                bottom = it.calculateBottomPadding(),
            )

        Box(modifier = modifier) {
            ElementsList(
                elementList = elementList,
                onElementClick = onElementClick,
                onAddNewElementClick = onAddNewElementClick,
            )
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun BoxScope.ElementsList(
    elementList: List<ElementItem>,
    onElementClick: (item: ElementItem) -> Unit,
    onAddNewElementClick: () -> Unit
) {
    val lazyColumnState = rememberLazyListState()
    val isFabVisible by remember {
        derivedStateOf {
            lazyColumnState.firstVisibleItemIndex == 0
        }
    }

    LaunchedEffect(elementList.size) {
        lazyColumnState.animateScrollToItem(0)
    }

    LazyColumn(
        state = lazyColumnState,
        contentPadding = PaddingValues(16.dp)
    ) {
        items(
            items = elementList,
            key = { element -> element.id }
        ) { element ->
            ElementColumnItem(
                element,
                onItemClick = onElementClick
            )
            Spacer(modifier = Modifier.height(8.dp))
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

@Composable
private fun ElementColumnItem(
    item: ElementItem,
    onItemClick: (item: ElementItem) -> Unit = {},
) {
    ElevatedCard(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
    ) {
        ListItem(
            modifier = Modifier.clickable { onItemClick(item) },
            overlineContent = { Text(text = item.name) },
            headlineContent = { Text(text = item.description) },
        )
    }
}

@ExperimentalAnimationApi
@Composable
private fun AddNewElementButton(
    modifier: Modifier,
    isVisible: Boolean,
    onClick: () -> Unit,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = scaleIn(),
        exit = scaleOut(),
    ) {
        FloatingActionButton(
            onClick = onClick,
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(id = R.string.cd_add_new_element),
            )
        }
    }
}
