package com.mkchtv.cleantemplate.common.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction

@Composable
fun Input(
    modifier: Modifier = Modifier,
    state: InputState = rememberInputState(hint = "", initialValue = ""),
    imeAction: ImeAction = ImeAction.Unspecified,
    onImeAction: () -> Unit = {},
) = OutlinedTextField(
    modifier = modifier
        .fillMaxWidth()
        .focusRequester(state.focusRequester),
    label = { Text(text = state.label) },
    value = state.value,
    onValueChange = state::onValueChange,
    textStyle = MaterialTheme.typography.bodyMedium,
    keyboardOptions = KeyboardOptions(imeAction = imeAction),
    keyboardActions = KeyboardActions(
        onAny = {
            onImeAction()
        },
    )
)

class InputState(
    val label: String,
    private val initialValue: String,
) {
    val focusRequester = FocusRequester()
    private var changed by mutableStateOf<String?>(null)

    fun onValueChange(newValue: String?) {
        changed = newValue
    }

    val value by derivedStateOf {
        changed ?: initialValue
    }

    companion object {
        val Saver: Saver<InputState, *> = listSaver(
            save = { listOf(it.label, it.initialValue, it.changed) },
            restore = {
                InputState(
                    label = it[0] ?: "",
                    initialValue = it[1] ?: "",
                ).also { state -> state.onValueChange(it[2]) }
            }
        )
    }
}

@Composable
fun rememberInputState(hint: String, initialValue: String): InputState =
    rememberSaveable(hint, initialValue, saver = InputState.Saver) {
        InputState(label = hint, initialValue = initialValue)
    }
