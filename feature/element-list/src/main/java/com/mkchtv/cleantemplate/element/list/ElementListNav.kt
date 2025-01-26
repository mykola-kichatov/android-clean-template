package com.mkchtv.cleantemplate.element.list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mkchtv.cleantemplate.auth.AuthProtectedScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalSharedTransitionApi
@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
fun NavGraphBuilder.elementListScreen(
    onElementClick: (itemId: Int) -> Unit,
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
            )
        }
    }
}

fun NavController.navigateToElementList() = navigate(NAV_DESTINATION_LIST)

const val NAV_DESTINATION_LIST = "list"
