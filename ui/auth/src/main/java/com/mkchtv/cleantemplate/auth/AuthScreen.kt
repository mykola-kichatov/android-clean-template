package com.mkchtv.cleantemplate.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mkchtv.cleantemplate.common.preview.ScreenPreviews
import com.mkchtv.cleantemplate.common.preview.ThemedPreview
import com.mkchtv.cleantemplate.ui.auth.R

@Composable
internal fun AuthScreen(
    onIntent: (Intent) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Button(
            onClick = {
                onIntent(Intent.AuthenticateClick)
            },
        ) {
            Text(text = stringResource(id = R.string.authenticate))
        }
    }
}

@ScreenPreviews
@Composable
private fun AuthScreenPreview() {
    ThemedPreview {
        AuthScreen(onIntent = {})
    }
}
