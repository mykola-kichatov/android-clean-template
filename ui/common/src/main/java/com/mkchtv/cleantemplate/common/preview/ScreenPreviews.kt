package com.mkchtv.cleantemplate.common.preview

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import androidx.compose.ui.tooling.preview.Preview

/**
 * The four views every screen preview shows: portrait, portrait dark, landscape and landscape dark.
 * Put it on a preview function whose body is wrapped in [ThemedPreview].
 */
@Preview(name = "portrait", widthDp = 360, heightDp = 640)
@Preview(
    name = "portrait dark",
    widthDp = 360,
    heightDp = 640,
    uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL,
)
@Preview(name = "landscape", widthDp = 640, heightDp = 360)
@Preview(
    name = "landscape dark",
    widthDp = 640,
    heightDp = 360,
    uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL,
)
annotation class ScreenPreviews
