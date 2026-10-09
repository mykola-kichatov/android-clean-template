package com.mkchtv.cleantemplate.common.preview

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.mkchtv.cleantemplate.common.theme.AppTheme

/**
 * Wraps a `@Preview` in the app theme. Dynamic color is off, so a preview shows the app's own
 * colors on every machine. Light or dark follows the preview's `uiMode`, see [ScreenPreviews].
 */
@Composable
fun ThemedPreview(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    AppTheme(darkTheme = darkTheme, dynamicColor = false) {
        Surface { content() }
    }
}
