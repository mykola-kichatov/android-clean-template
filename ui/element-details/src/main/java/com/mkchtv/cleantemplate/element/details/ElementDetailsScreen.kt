package com.mkchtv.cleantemplate.element.details

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mkchtv.cleantemplate.ui.element.details.R
import com.mkchtv.cleantemplate.common.component.ConfirmDialog
import com.mkchtv.cleantemplate.common.component.LoadingScreen
import com.mkchtv.cleantemplate.common.compositionlocal.LocalSharedTransitionScope
import com.mkchtv.cleantemplate.element.details.component.CreateNewElement
import com.mkchtv.cleantemplate.element.details.component.TopBar
import com.mkchtv.cleantemplate.element.details.component.UpdateExistedElement
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalSharedTransitionApi
@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@Composable
internal fun ElementDetailsScreen(
    uiState: UiState,
    onIntent: (Intent) -> Unit,
) {
    var showConfirmDeletionDialog by remember { mutableStateOf(false) }
    val sharedElementScope = checkNotNull(LocalSharedTransitionScope.current) { "No shared element scope" }

    Scaffold(
        topBar = {
            TopBar(
                isScreenLoading = uiState.isLoading,
                element = uiState.element,
                onBackClick = { onIntent(Intent.BackClick) },
                onDeleteRequested = { showConfirmDeletionDialog = true },
            )
        }
    ) { paddingValues ->
        Box {
            val modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                )

            when {
                uiState.isLoading -> LoadingScreen(
                    modifier = Modifier.fillMaxSize(),
                )

                uiState.element == null -> CreateNewElement(
                    onCreateRequested = { name, desc ->
                        onIntent(Intent.CreateElement(name, desc))
                    }
                )

                else -> with(sharedElementScope) {
                    UpdateExistedElement(
                        element = uiState.element,
                        onUpdateRequested = { name, desc, imageUrl ->
                            onIntent(Intent.UpdateElement(name, desc, imageUrl))
                        },
                    )
                }
            }

            if (showConfirmDeletionDialog)
                ConfirmDialog(
                    title = stringResource(id = R.string.confirm_delete),
                    onDismissRequest = { showConfirmDeletionDialog = false },
                    onConfirm = {
                        showConfirmDeletionDialog = false
                        onIntent(Intent.DeleteElement)
                    }
                )
        }
    }
}
