package com.mkchtv.cleantemplate.element.details

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mkchtv.cleantemplate.R
import com.mkchtv.cleantemplate.common.component.ConfirmDialog
import com.mkchtv.cleantemplate.common.component.Input
import com.mkchtv.cleantemplate.common.component.LoadingScreen
import com.mkchtv.cleantemplate.common.component.rememberInputState
import com.mkchtv.cleantemplate.domain.element.entity.Element
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@Composable
fun ElementDetailsScreen(
    screenState: ElementDetailsScreenState,
    onBackClick: () -> Unit,
    onCreateConfirmed: (name: String, description: String) -> Unit,
    onUpdateConfirmed: (name: String, description: String, imageUrl: String) -> Unit,
    onDeleteConfirmed: () -> Unit,
) {
    var showConfirmDeletionDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(
                screenState = screenState,
                onBackClick = onBackClick,
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

            Crossfade(
                modifier = modifier,
                targetState = screenState,
                label = "Element details",
            ) { state ->
                when (state) {
                    ElementDetailsScreenState.Loading -> LoadingScreen(
                        modifier = Modifier.fillMaxSize(),
                    )

                    is ElementDetailsScreenState.CreateNewElement -> CreateNewElement(
                        onCreateConfirmed = onCreateConfirmed,
                    )

                    is ElementDetailsScreenState.UpdateExistedElement -> UpdateExistedElement(
                        element = state.element,
                        onUpdateConfirmed = onUpdateConfirmed,
                    )
                }
            }

            if (showConfirmDeletionDialog)
                ConfirmDialog(
                    title = stringResource(id = R.string.confirm_delete),
                    onDismissRequest = { showConfirmDeletionDialog = false },
                    onConfirm = {
                        showConfirmDeletionDialog = false
                        onDeleteConfirmed()
                    }
                )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun TopBar(
    screenState: ElementDetailsScreenState,
    onBackClick: () -> Unit,
    onDeleteRequested: () -> Unit,
) {
    val titleRes: Int = remember(screenState) {
        when (screenState) {
            ElementDetailsScreenState.Loading -> R.string.loading
            ElementDetailsScreenState.CreateNewElement -> R.string.create_new_element
            is ElementDetailsScreenState.UpdateExistedElement -> R.string.edit_element_details
        }
    }

    val title = stringResource(id = titleRes)

    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.cd_go_back),
                )
            }
        },
        actions = {
            if (screenState is ElementDetailsScreenState.UpdateExistedElement)
                IconButton(onClick = onDeleteRequested) {
                    Icon(Icons.Filled.Delete, stringResource(id = R.string.cd_delete))
                }
        }
    )
}

@Composable
private fun CreateNewElement(
    onCreateConfirmed: (name: String, description: String) -> Unit,
) {
    val nameInputState = rememberInputState(
        hint = stringResource(id = R.string.name),
        initialValue = "",
    )
    val descInputState = rememberInputState(
        hint = stringResource(id = R.string.description),
        initialValue = "",
    )

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        nameInputState.focusRequester.requestFocus()
    }

    val onCreateAction = {
        keyboardController?.hide()
        onCreateConfirmed(
            nameInputState.value, descInputState.value
        )
    }

    Column(
        modifier = Modifier
            .imePadding()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Input(
            state = nameInputState,
            imeAction = ImeAction.Next,
            onImeAction = {
                descInputState.focusRequester.requestFocus()
            },
        )
        Spacer(modifier = Modifier.height(8.dp))
        Input(
            state = descInputState,
            imeAction = ImeAction.Done,
            onImeAction = onCreateAction,
        )
        Spacer(modifier = Modifier.height(24.dp))
        ElevatedButton(onClick = onCreateAction) {
            Text(text = stringResource(id = R.string.create))
        }
    }
}

@Composable
private fun UpdateExistedElement(
    element: Element,
    onUpdateConfirmed: (name: String, description: String, imageUrl: String) -> Unit,
) {
    val nameInputState = rememberInputState(
        hint = stringResource(id = R.string.name),
        initialValue = element.name,
    )
    val descInputState = rememberInputState(
        hint = stringResource(id = R.string.description),
        initialValue = element.description,
    )
    val keyboardController = LocalSoftwareKeyboardController.current

    val onUpdateAction = {
        keyboardController?.hide()
        onUpdateConfirmed(
            nameInputState.value, descInputState.value, element.imageUrl
        )
    }

    Column(
        modifier = Modifier
            .imePadding()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = element.imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4f / 3f),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Input(
            state = nameInputState,
            imeAction = ImeAction.Next,
            onImeAction = {
                descInputState.focusRequester.requestFocus()
            },
        )
        Spacer(modifier = Modifier.height(8.dp))
        Input(
            state = descInputState,
            imeAction = ImeAction.Done,
            onImeAction = onUpdateAction,
        )
        Spacer(modifier = Modifier.height(24.dp))
        ElevatedButton(onClick = onUpdateAction) {
            Text(text = stringResource(id = R.string.update))
        }
    }
}
