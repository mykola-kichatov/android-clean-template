package com.mkchtv.cleantemplate.domain.settings.entity

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ThemeModeTest {

    @Test
    fun `system follows the phone`() {
        // [GIVEN]
        val themeMode = ThemeMode.SYSTEM

        // [WHEN]
        val onDarkPhone = themeMode.useDarkTheme(systemInDarkTheme = true)
        val onLightPhone = themeMode.useDarkTheme(systemInDarkTheme = false)

        // [THEN]
        Assertions.assertTrue(onDarkPhone)
        Assertions.assertFalse(onLightPhone)
    }

    @Test
    fun `light stays light on a dark phone`() {
        // [GIVEN]
        val themeMode = ThemeMode.LIGHT

        // [WHEN]
        val onDarkPhone = themeMode.useDarkTheme(systemInDarkTheme = true)
        val onLightPhone = themeMode.useDarkTheme(systemInDarkTheme = false)

        // [THEN]
        Assertions.assertFalse(onDarkPhone)
        Assertions.assertFalse(onLightPhone)
    }

    @Test
    fun `dark stays dark on a light phone`() {
        // [GIVEN]
        val themeMode = ThemeMode.DARK

        // [WHEN]
        val onDarkPhone = themeMode.useDarkTheme(systemInDarkTheme = true)
        val onLightPhone = themeMode.useDarkTheme(systemInDarkTheme = false)

        // [THEN]
        Assertions.assertTrue(onDarkPhone)
        Assertions.assertTrue(onLightPhone)
    }

    @Test
    fun `entry names stay the same, they are the stored values`() {
        // [GIVEN]
        val storedNames = setOf("SYSTEM", "LIGHT", "DARK")

        // [WHEN]
        val names = ThemeMode.entries.map { it.name }.toSet()

        // [THEN]
        Assertions.assertEquals(
            storedNames,
            names,
            "Installs store these names. A renamed entry silently resets their setting.",
        )
    }
}
