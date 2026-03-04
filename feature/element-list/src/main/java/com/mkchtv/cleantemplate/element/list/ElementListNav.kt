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
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.runtime.LaunchedEffect
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.compose.ui.platform.LocalContext
import com.mkchtv.cleantemplate.element.list.Effect.NavigateToAddElement
import com.mkchtv.cleantemplate.element.list.Effect.NavigateToElement
import com.mkchtv.cleantemplate.element.list.Effect.ShowError

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
            val viewModel = hiltViewModel<ElementListViewModel>()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            val context = LocalContext.current

            LaunchedEffect(viewModel) {
                viewModel.effects.collectLatest { effect ->
                    when (effect) {
                        is ShowError -> Toast.makeText(context, effect.message, LENGTH_SHORT).show()
                        is NavigateToElement -> onElementClick(effect.itemId)
                        NavigateToAddElement -> onAddNewElementClick()
                    }
                }
            }

            ElementListScreen(
                uiState = uiState.value,
                onIntent = viewModel::onIntent,
            )
        }
    }
}

fun NavController.navigateToElementList() = navigate(NAV_DESTINATION_LIST)

const val NAV_DESTINATION_LIST = "list"
