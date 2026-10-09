package com.mkchtv.cleantemplate.domain.settings.repository

import com.mkchtv.cleantemplate.domain.settings.entity.Appearance
import com.mkchtv.cleantemplate.domain.settings.entity.ThemeMode
import kotlinx.coroutines.flow.Flow

/** App settings that survive a restart. */
interface SettingsRepository {

    /**
     * Emits the stored appearance, and again on every change. Never empty: a fresh install gets
     * the defaults of [Appearance].
     */
    val appearance: Flow<Appearance>

    suspend fun setThemeMode(themeMode: ThemeMode)

    suspend fun setDynamicColor(enabled: Boolean)
}
