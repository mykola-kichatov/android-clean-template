package com.mkchtv.cleantemplate.element.details.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mkchtv.cleantemplate.common.component.Input
import com.mkchtv.cleantemplate.common.component.rememberInputState
import com.mkchtv.cleantemplate.common.compositionlocal.LocalAnimatedVisibilityScope
import com.mkchtv.cleantemplate.ui.common.R as commonRes

@Composable
internal fun CreateNewElement(
    onCreateRequested: (name: String, desc: String) -> Unit,
) {
    val nameInputState = rememberInputState(
        label = stringResource(id = commonRes.string.name),
        initialValue = "",
    )
    val descInputState = rememberInputState(
        label = stringResource(id = commonRes.string.description),
        initialValue = "",
    )

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        nameInputState.focusRequester.requestFocus()
    }

    val onCreateAction = {
        keyboardController?.hide()
        onCreateRequested(nameInputState.value, descInputState.value)
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

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalAnimationApi::class)
@Preview
@Composable
private fun ElementListEmptyPreview() {
    Surface {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                CompositionLocalProvider(LocalAnimatedVisibilityScope provides this) {
                    CreateNewElement(
                        onCreateRequested = { _, _ -> },
                    )
                }
            }
        }
    }
}
