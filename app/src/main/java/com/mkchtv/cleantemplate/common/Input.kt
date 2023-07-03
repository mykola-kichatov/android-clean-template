package com.mkchtv.cleantemplate.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Input(
    modifier: Modifier = Modifier,
    state: InputState = rememberInputState(hint = "", initialValue = "")
) {
    TextField(
        modifier = modifier,
        value = state.value,
        onValueChange = state::onValueChange,
        textStyle = if (state.isHint) {
            MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
        } else {
            MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
        },
    )
}

class InputState(
    private val hint: String,
    private val initialValue: String
) {
    private var changed by mutableStateOf<String?>(null)

    fun onValueChange(newValue: String?) {
        changed = newValue
    }

    val value by derivedStateOf {
        changed ?: initialValue
    }

    val isHint = value == hint

    companion object {
        val Saver: Saver<InputState, *> = listSaver(
            save = { listOf(it.hint, it.initialValue, it.changed) },
            restore = {
                InputState(
                    hint = it[0] ?: "",
                    initialValue = it[1] ?: "",
                ).also { state -> state.onValueChange(it[2]) }
            }
        )
    }
}

@Composable
fun rememberInputState(hint: String, initialValue: String): InputState =
    rememberSaveable(hint, initialValue, saver = InputState.Saver) {
        InputState(hint = hint, initialValue = initialValue)
    }