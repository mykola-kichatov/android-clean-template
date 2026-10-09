package com.mkchtv.cleantemplate.common.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Guards the color scheme in Color.kt and Theme.kt.
 *
 * `lightColorScheme()` and `darkColorScheme()` fill every role they are not given with Material's
 * own baseline, which is purple. So a role left out of Theme.kt paints its bars, sheets and dialogs
 * purple-grey next to the app's colors, and nothing else fails. This test fails instead.
 *
 * It holds for any brand color, except Material's own baseline purple.
 */
class ColorSchemeTest {

    @Test
    fun `no color role falls back to Material's baseline`() {
        // [GIVEN]
        val schemes = mapOf(
            "light" to (LightColors to lightColorScheme()),
            "dark" to (DarkColors to darkColorScheme()),
        )

        // [WHEN]
        val fallbacks = schemes.flatMap { (theme, schemeAndBaseline) ->
            val (scheme, baseline) = schemeAndBaseline
            scheme.fallbacksTo(baseline).map { (role, color) -> "$theme $role ${color.hex()}" }
        }

        // [THEN]
        Assertions.assertEquals(
            emptyList<String>(),
            fallbacks,
            "These roles have Material's baseline value. Are they set in Theme.kt?",
        )
    }

    @Test
    fun `the check finds a role that is left out`() {
        // [GIVEN]
        val scheme = lightColorScheme(primary = Color(0xFF9A4612))

        // [WHEN]
        val fallbacks = scheme.fallbacksTo(lightColorScheme())

        // [THEN]
        Assertions.assertTrue("surfaceContainer" in fallbacks, "fallbacks: ${fallbacks.keys}")
        Assertions.assertFalse("primary" in fallbacks, "fallbacks: ${fallbacks.keys}")
    }

    /**
     * The roles whose color is the [baseline]'s. Pure white and black are skipped: they are tone 100
     * and tone 0 in every palette, so they match the baseline even when they are set.
     */
    private fun ColorScheme.fallbacksTo(baseline: ColorScheme): Map<String, Color> {
        val baselineRoles = baseline.roles()
        return roles().filter { (role, color) ->
            color == baselineRoles.getValue(role) && color != Color.White && color != Color.Black
        }
    }

    private fun Color.hex(): String = "#%08X".format(toArgb())

    /** All 48 roles of Material 3's color scheme. */
    private fun ColorScheme.roles(): Map<String, Color> = mapOf(
        "primary" to primary,
        "onPrimary" to onPrimary,
        "primaryContainer" to primaryContainer,
        "onPrimaryContainer" to onPrimaryContainer,
        "inversePrimary" to inversePrimary,
        "secondary" to secondary,
        "onSecondary" to onSecondary,
        "secondaryContainer" to secondaryContainer,
        "onSecondaryContainer" to onSecondaryContainer,
        "tertiary" to tertiary,
        "onTertiary" to onTertiary,
        "tertiaryContainer" to tertiaryContainer,
        "onTertiaryContainer" to onTertiaryContainer,
        "background" to background,
        "onBackground" to onBackground,
        "surface" to surface,
        "onSurface" to onSurface,
        "surfaceVariant" to surfaceVariant,
        "onSurfaceVariant" to onSurfaceVariant,
        "surfaceTint" to surfaceTint,
        "inverseSurface" to inverseSurface,
        "inverseOnSurface" to inverseOnSurface,
        "error" to error,
        "onError" to onError,
        "errorContainer" to errorContainer,
        "onErrorContainer" to onErrorContainer,
        "outline" to outline,
        "outlineVariant" to outlineVariant,
        "scrim" to scrim,
        "surfaceBright" to surfaceBright,
        "surfaceDim" to surfaceDim,
        "surfaceContainer" to surfaceContainer,
        "surfaceContainerHigh" to surfaceContainerHigh,
        "surfaceContainerHighest" to surfaceContainerHighest,
        "surfaceContainerLow" to surfaceContainerLow,
        "surfaceContainerLowest" to surfaceContainerLowest,
        "primaryFixed" to primaryFixed,
        "primaryFixedDim" to primaryFixedDim,
        "onPrimaryFixed" to onPrimaryFixed,
        "onPrimaryFixedVariant" to onPrimaryFixedVariant,
        "secondaryFixed" to secondaryFixed,
        "secondaryFixedDim" to secondaryFixedDim,
        "onSecondaryFixed" to onSecondaryFixed,
        "onSecondaryFixedVariant" to onSecondaryFixedVariant,
        "tertiaryFixed" to tertiaryFixed,
        "tertiaryFixedDim" to tertiaryFixedDim,
        "onTertiaryFixed" to onTertiaryFixed,
        "onTertiaryFixedVariant" to onTertiaryFixedVariant,
    )
}
