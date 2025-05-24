package com.mkchtv.cleantemplate.element.details

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
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
import com.mkchtv.cleantemplate.feature.elementdetails.R
import com.mkchtv.cleantemplate.feature.common.R as commonRes
import com.mkchtv.cleantemplate.common.component.ConfirmDialog
import com.mkchtv.cleantemplate.common.component.Input
import com.mkchtv.cleantemplate.common.component.LoadingScreen
import com.mkchtv.cleantemplate.common.component.rememberInputState
import com.mkchtv.cleantemplate.common.compositionlocal.LocalNavAnimatedVisibilityScope
import com.mkchtv.cleantemplate.common.compositionlocal.LocalSharedTransitionScope
import com.mkchtv.cleantemplate.domain.element.entity.Element
import com.mkchtv.cleantemplate.element.details.ElementDetailsScreenState.CreateNewElement
import com.mkchtv.cleantemplate.element.details.ElementDetailsScreenState.Loading
import com.mkchtv.cleantemplate.element.details.ElementDetailsScreenState.UpdateExistedElement
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalSharedTransitionApi
@ExperimentalMaterial3Api
@ExperimentalCoroutinesApi
@Composable
internal fun ElementDetailsScreen(
    screenState: ElementDetailsScreenState,
    onBackClick: () -> Unit,
    onCreateConfirmed: (name: String, description: String) -> Unit,
    onUpdateConfirmed: (name: String, description: String, imageUrl: String) -> Unit,
    onDeleteConfirmed: () -> Unit,
) {
    var showConfirmDeletionDialog by remember { mutableStateOf(false) }
    val sharedTransitionScope =
        LocalSharedTransitionScope.current ?: throw IllegalStateException("No shared element scope")

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
                    Loading -> LoadingScreen(
                        modifier = Modifier.fillMaxSize(),
                    )

                    is CreateNewElement -> CreateNewElement(
                        onCreateConfirmed = onCreateConfirmed,
                    )

                    is UpdateExistedElement -> with(sharedTransitionScope) {
                        UpdateExistedElement(
                            element = state.element,
                            onUpdateConfirmed = onUpdateConfirmed,
                        )
                    }
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
            Loading -> commonRes.string.loading
            CreateNewElement -> R.string.create_new_element
            is UpdateExistedElement -> R.string.edit_element_details
        }
    }

    val title = stringResource(id = titleRes)

    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = commonRes.string.cd_go_back),
                )
            }
        },
        actions = {
            if (screenState is UpdateExistedElement)
                IconButton(onClick = onDeleteRequested) {
                    Icon(Icons.Filled.Delete, stringResource(id = commonRes.string.cd_delete))
                }
        }
    )
}

@Composable
private fun CreateNewElement(
    onCreateConfirmed: (name: String, description: String) -> Unit,
) {
    val nameInputState = rememberInputState(
        hint = stringResource(id = commonRes.string.name),
        initialValue = "",
    )
    val descInputState = rememberInputState(
        hint = stringResource(id = commonRes.string.description),
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
            Text(text = stringResource(id = commonRes.string.create))
        }
    }
}

@ExperimentalSharedTransitionApi
@Composable
private fun SharedTransitionScope.UpdateExistedElement(
    element: Element,
    onUpdateConfirmed: (name: String, description: String, imageUrl: String) -> Unit,
) {
    val nameInputState = rememberInputState(
        hint = stringResource(id = commonRes.string.name),
        initialValue = element.name,
    )
    val descInputState = rememberInputState(
        hint = stringResource(id = commonRes.string.description),
        initialValue = element.description,
    )
    val keyboardController = LocalSoftwareKeyboardController.current

    val onUpdateAction = {
        keyboardController?.hide()
        onUpdateConfirmed(
            nameInputState.value, descInputState.value, element.imageUrl
        )
    }
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
        ?: throw IllegalStateException("No shared element scope")

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
                .sharedElement(
                    sharedContentState = rememberSharedContentState(key = "${element.id}_img"),
                    animatedVisibilityScope = animatedVisibilityScope,
                )
                .fillMaxWidth()
                .aspectRatio(4f / 3f),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Input(
            modifier = Modifier.sharedBounds(
                sharedContentState = rememberSharedContentState(key = "${element.id}_name"),
                animatedVisibilityScope = animatedVisibilityScope,
            ),
            state = nameInputState,
            imeAction = ImeAction.Next,
            onImeAction = {
                descInputState.focusRequester.requestFocus()
            },
        )
        Spacer(modifier = Modifier.height(8.dp))
        Input(
            modifier = Modifier.sharedBounds(
                sharedContentState = rememberSharedContentState(key = "${element.id}_desc"),
                animatedVisibilityScope = animatedVisibilityScope,
            ),
            state = descInputState,
            imeAction = ImeAction.Done,
            onImeAction = onUpdateAction,
        )
        Spacer(modifier = Modifier.height(24.dp))
        ElevatedButton(onClick = onUpdateAction) {
            Text(text = stringResource(id = commonRes.string.update))
        }
    }
}
