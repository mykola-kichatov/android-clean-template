package com.mkchtv.cleantemplate.element.details.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mkchtv.cleantemplate.common.component.Input
import com.mkchtv.cleantemplate.common.component.rememberInputState
import com.mkchtv.cleantemplate.common.compositionlocal.LocalAnimatedVisibilityScope
import com.mkchtv.cleantemplate.domain.element.entity.Element
import com.mkchtv.cleantemplate.ui.common.R as commonRes

@ExperimentalSharedTransitionApi
@Composable
internal fun SharedTransitionScope.UpdateExistedElement(
    element: Element,
    onUpdateRequested: (name: String, desc: String, imageUrl: String) -> Unit,
) {
    val nameInputState = rememberInputState(
        label = stringResource(id = commonRes.string.name),
        initialValue = element.name,
    )
    val descInputState = rememberInputState(
        label = stringResource(id = commonRes.string.description),
        initialValue = element.description,
    )
    val keyboardController = LocalSoftwareKeyboardController.current

    val onUpdateAction = {
        keyboardController?.hide()
        onUpdateRequested(nameInputState.value, descInputState.value, element.imageUrl)
    }
    val visibilityScope = checkNotNull(LocalAnimatedVisibilityScope.current) { "No shared element scope" }

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
                    animatedVisibilityScope = visibilityScope,
                )
                .fillMaxWidth()
                .aspectRatio(4f / 3f),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Input(
            modifier = Modifier.sharedBounds(
                sharedContentState = rememberSharedContentState(key = "${element.id}_name"),
                animatedVisibilityScope = visibilityScope,
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
                animatedVisibilityScope = visibilityScope,
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

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalAnimationApi::class)
@Preview
@Composable
private fun ElementListEmptyPreview() {
    Surface {
        SharedTransitionLayout {
            val sharedTransitionScope = this
            AnimatedVisibility(visible = true) {
                CompositionLocalProvider(LocalAnimatedVisibilityScope provides this) {
                    sharedTransitionScope.UpdateExistedElement(
                        element = Element(0, "Element 1", "Desc 1", ""),
                        onUpdateRequested = { _, _, _ -> },
                    )
                }
            }
        }
    }
}
