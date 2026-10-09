package com.mkchtv.cleantemplate.data.settings.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * The file that holds the app's settings. The name is permanent: rename it and every existing
 * install silently starts from the defaults.
 */
private const val SETTINGS_FILE = "settings"

/**
 * The one settings store of the process.
 *
 * DataStore throws if a second instance is ever created on the same file. A top-level delegate
 * cannot be created twice, which is why the store is not built inside the `@Provides` function.
 *
 * A corrupt file resets to the defaults instead of crashing. That suits settings, which the user
 * can pick again. It does not suit data nobody can recreate.
 */
private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(
    name = SETTINGS_FILE,
    corruptionHandler = ReplaceFileCorruptionHandler { emptyPreferences() },
)

@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {

    @Provides
    @Singleton
    fun provideSettingsDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.settingsDataStore
}
