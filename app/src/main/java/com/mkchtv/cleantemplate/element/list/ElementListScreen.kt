package com.mkchtv.cleantemplate.element.list

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.mkchtv.cleantemplate.R
import com.mkchtv.cleantemplate.common.component.LoadingScreen
import com.mkchtv.cleantemplate.element.list.entity.ElementItem
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalSharedTransitionApi
@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
fun ElementListScreen(
    screenState: ElementListScreenState,
    onElementClick: (item: ElementItem) -> Unit,
    onAddNewElementClick: () -> Unit,
    onPullNewElementRequested: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) = Scaffold(
    modifier = Modifier.systemBarsPadding(),
    topBar = {
        TopBar(onPullNewElementRequested = onPullNewElementRequested)
    },
) {
    val modifier = Modifier
        .fillMaxSize()
        .padding(
            top = it.calculateTopPadding(),
            bottom = it.calculateBottomPadding(),
        )

    Crossfade(
        modifier = modifier,
        targetState = screenState,
        label = "Element list",
    ) { state ->
        when (state) {
            ElementListScreenState.Loading -> LoadingScreen(
                modifier = Modifier.fillMaxSize(),
            )

            is ElementListScreenState.ListLoaded -> with(sharedTransitionScope) {
                ElementsList(
                    modifier = Modifier.fillMaxSize(),
                    elementList = state.elements,
                    onElementClick = onElementClick,
                    onAddNewElementClick = onAddNewElementClick,
                    animatedVisibilityScope = animatedVisibilityScope,
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun TopBar(
    onPullNewElementRequested: () -> Unit,
) = TopAppBar(
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
