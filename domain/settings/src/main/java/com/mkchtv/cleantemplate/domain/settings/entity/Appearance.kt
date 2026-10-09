package com.mkchtv.cleantemplate.domain.settings.entity

/**
 * Everything the theme needs, in one value. The defaults are what a fresh install gets.
 *
 * @param dynamicColor paints the app from the user's wallpaper on Android 12 and newer. Older
 * versions keep the app's own colors, whatever this says.
 */
data class Appearance(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val dynamicColor: Boolean = true,
)
