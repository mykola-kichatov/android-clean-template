package com.mkchtv.cleantemplate.list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mkchtv.cleantemplate.domain.common.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
fun NavGraphBuilder.elementListScreen(
    onElementClick: (item: ElementItem) -> Unit = {},
    onAddNewElementClick: () -> Unit = {},
) {
    composable(route = Constants.NAV_DESTINATION_LIST) {
        val viewModel = hiltViewModel<ElementsListViewModel>()
        val elementList = viewModel.elementsState.collectAsStateWithLifecycle()
        ElementListScreen(
            elementList = elementList.value,
            onElementClick = onElementClick,
            onAddNewElementClick = onAddNewElementClick
        )
    }
}