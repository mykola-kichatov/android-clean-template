package com.mkchtv.cleantemplate.domain.settings.entity

/**
 * The theme the user picked in Settings.
 *
 * Stored by [name], never by ordinal, so reordering the entries cannot change what an existing
 * install reads back.
 */
enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK,
    ;

    /**
     * Whether the app draws dark right now.
     *
     * @param systemInDarkTheme whether the phone itself is in dark mode. Only [SYSTEM] looks at it.
     */
    fun useDarkTheme(systemInDarkTheme: Boolean): Boolean = when (this) {
        SYSTEM -> systemInDarkTheme
        LIGHT -> false
        DARK -> true
    }
}
