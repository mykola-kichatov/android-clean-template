package com.mkchtv.cleantemplate.common.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mkchtv.cleantemplate.feature.common.R

@ExperimentalMaterial3Api
@Composable
fun ConfirmDialog(
    modifier: Modifier = Modifier,
    title: String,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    confirmButtonText: String = stringResource(id = R.string.confirm),
    dismissButtonText: String = stringResource(id = R.string.cancel),
) {
    AlertDialog(
        modifier = modifier,
        title = { Text(title) },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(confirmButtonText)
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text(dismissButtonText)
            }
        },
    )
}
