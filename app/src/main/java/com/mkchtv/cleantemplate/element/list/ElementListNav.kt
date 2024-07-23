package com.mkchtv.cleantemplate.element.list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mkchtv.cleantemplate.auth.AuthProtectedScreen
import com.mkchtv.cleantemplate.element.list.entity.ElementItem
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalSharedTransitionApi
@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
fun NavGraphBuilder.elementListScreen(
    sharedTransitionScope: SharedTransitionScope,
    onElementClick: (item: ElementItem) -> Unit,
    onAddNewElementClick: () -> Unit,
) {
    composable(route = NAV_DESTINATION_LIST) {
        AuthProtectedScreen {
            val viewModel = hiltViewModel<ElementsListViewModel>()
            val screenState = viewModel.elements.collectAsStateWithLifecycle()
            ElementListScreen(
                elements = screenState.value,
                onElementClick = onElementClick,
                onAddNewElementClick = onAddNewElementClick,
                onPullNewElementRequested = { viewModel.onPullNewElementRequested() },
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = this@composable,
            )
        }
    }
}

const val NAV_DESTINATION_LIST = "list"
