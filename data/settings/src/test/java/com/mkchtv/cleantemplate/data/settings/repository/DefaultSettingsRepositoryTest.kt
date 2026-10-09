package com.mkchtv.cleantemplate.data.settings.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mkchtv.cleantemplate.domain.settings.entity.Appearance
import com.mkchtv.cleantemplate.domain.settings.entity.ThemeMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

/**
 * Runs on a real DataStore in a temporary folder, not on a mock: the file format and the
 * fallbacks are what this class is about.
 */
class DefaultSettingsRepositoryTest {

    @Test
    fun `an untouched store gives the defaults`(@TempDir dir: File) = runTest {
        // [GIVEN]
        val repository = DefaultSettingsRepository(dataStore = dataStore(dir, backgroundScope))

        // [WHEN]
        val appearance = repository.appearance.first()

        // [THEN]
        Assertions.assertEquals(
            Appearance(themeMode = ThemeMode.SYSTEM, dynamicColor = true),
            appearance,
        )
    }

    @Test
    fun `written values are read back`(@TempDir dir: File) = runTest {
        // [GIVEN]
        val repository = DefaultSettingsRepository(dataStore = dataStore(dir, backgroundScope))

        // [WHEN]
        repository.setThemeMode(ThemeMode.DARK)
        repository.setDynamicColor(enabled = false)

        // [THEN]
        Assertions.assertEquals(
            Appearance(themeMode = ThemeMode.DARK, dynamicColor = false),
            repository.appearance.first(),
        )
    }

    @Test
    fun `the file keeps the permanent keys and the theme mode by name`(@TempDir dir: File) =
        runTest {
            // [GIVEN]
            val dataStore = dataStore(dir, backgroundScope)
            val repository = DefaultSettingsRepository(dataStore = dataStore)

            // [WHEN]
            repository.setThemeMode(ThemeMode.LIGHT)
            repository.setDynamicColor(enabled = false)

            // [THEN]
            val stored = dataStore.data.first().asMap().mapKeys { (key, _) -> key.name }
            Assertions.assertEquals(
                mapOf("theme_mode" to "LIGHT", "dynamic_color" to false),
                stored,
                "Installs already hold these keys and values. A change resets their settings.",
            )
        }

    @Test
    fun `an unknown theme mode in the file falls back to the default`(@TempDir dir: File) =
        runTest {
            // [GIVEN] a file written by a version that knew a mode this one does not
            val dataStore = dataStore(dir, backgroundScope)
            dataStore.edit { preferences ->
                preferences[stringPreferencesKey("theme_mode")] = "MIDNIGHT"
            }

            // [WHEN]
            val appearance = DefaultSettingsRepository(dataStore = dataStore).appearance.first()

            // [THEN]
            Assertions.assertEquals(ThemeMode.SYSTEM, appearance.themeMode)
        }

    private fun dataStore(dir: File, scope: CoroutineScope): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(scope = scope) {
            File(dir, "settings.preferences_pb")
        }
}
