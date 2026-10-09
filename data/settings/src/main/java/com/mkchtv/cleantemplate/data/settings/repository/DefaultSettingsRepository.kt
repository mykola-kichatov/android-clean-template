package com.mkchtv.cleantemplate.data.settings.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mkchtv.cleantemplate.domain.settings.entity.Appearance
import com.mkchtv.cleantemplate.domain.settings.entity.ThemeMode
import com.mkchtv.cleantemplate.domain.settings.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Permanent keys: rename one and the user's setting is gone without a word.
 * [ThemeMode] is stored by name, so reordering the enum cannot shift what an install reads back.
 */
private val KEY_THEME_MODE = stringPreferencesKey("theme_mode")
private val KEY_DYNAMIC_COLOR = booleanPreferencesKey("dynamic_color")

private val DEFAULTS = Appearance()

@Singleton
internal class DefaultSettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : SettingsRepository {

    override val appearance: Flow<Appearance> = dataStore.data
        .catch { throwable ->
            // A failed read is not worth a crash. Draw the defaults and carry on.
            if (throwable is IOException) emit(emptyPreferences()) else throw throwable
        }
        .map { preferences -> preferences.toAppearance() }

    override suspend fun setThemeMode(themeMode: ThemeMode) {
        dataStore.edit { preferences -> preferences[KEY_THEME_MODE] = themeMode.name }
    }

    override suspend fun setDynamicColor(enabled: Boolean) {
        dataStore.edit { preferences -> preferences[KEY_DYNAMIC_COLOR] = enabled }
    }
}

private fun Preferences.toAppearance() = Appearance(
    themeMode = this[KEY_THEME_MODE].toThemeMode(),
    dynamicColor = this[KEY_DYNAMIC_COLOR] ?: DEFAULTS.dynamicColor,
)

/**
 * An unknown name means a downgrade from a version that knew more modes, or an edited file.
 * Fall back to the default rather than throw.
 */
private fun String?.toThemeMode() =
    ThemeMode.entries.firstOrNull { themeMode -> themeMode.name == this } ?: DEFAULTS.themeMode
