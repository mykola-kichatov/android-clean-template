package com.mkchtv.cleantemplate.element.details.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mkchtv.cleantemplate.common.preview.ThemedPreview

/** The form's main button. While [isSaving], it is disabled and shows a spinner. */
@Composable
internal fun SaveButton(
    text: String,
    isSaving: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) = ElevatedButton(
    onClick = onClick,
    modifier = modifier,
    enabled = !isSaving,
) {
    if (isSaving) {
        CircularProgressIndicator(
            modifier = Modifier.size(ButtonDefaults.IconSize),
            color = LocalContentColor.current,
            strokeWidth = 2.dp,
        )
        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
    }
    Text(text = text)
}

@Preview
@Composable
private fun SaveButtonPreview() {
    ThemedPreview {
        Column {
            SaveButton(text = "Update", isSaving = false, onClick = {})
            SaveButton(text = "Update", isSaving = true, onClick = {})
        }
    }
}
