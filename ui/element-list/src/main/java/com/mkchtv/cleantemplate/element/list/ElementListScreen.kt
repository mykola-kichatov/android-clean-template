package com.mkchtv.cleantemplate.element.list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mkchtv.cleantemplate.common.component.LoadingScreen
import com.mkchtv.cleantemplate.common.compositionlocal.LocalSharedTransitionScope
import com.mkchtv.cleantemplate.element.list.Intent.AddNewElement
import com.mkchtv.cleantemplate.element.list.Intent.ElementClick
import com.mkchtv.cleantemplate.element.list.Intent.PullNewElement
import com.mkchtv.cleantemplate.element.list.component.ElementList
import com.mkchtv.cleantemplate.element.list.component.TopBar
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalSharedTransitionApi
@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@Composable
internal fun ElementListScreen(
    uiState: UiState,
    onIntent: (Intent) -> Unit,
) = Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = { TopBar(onPullNewElementRequested = { onIntent(PullNewElement) }) },
) { scaffoldPadding ->
    val sharedTransitionScope =
        checkNotNull(LocalSharedTransitionScope.current) { "No shared element scope" }
    when {
        uiState.isLoading && uiState.elements.isEmpty() -> LoadingScreen(Modifier.fillMaxSize())
        else -> with(sharedTransitionScope) {
            ElementList(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding),
                elements = uiState.elements,
                onElementClick = { element -> onIntent(ElementClick(element.id)) },
                onAddNewElementClick = { onIntent(AddNewElement) },
            )
        }
    }
}
