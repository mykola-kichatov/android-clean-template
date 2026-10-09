package com.mkchtv.cleantemplate.domain.settings.usecase

import com.mkchtv.cleantemplate.domain.settings.entity.ThemeMode
import com.mkchtv.cleantemplate.domain.settings.repository.SettingsRepository
import javax.inject.Inject

class SetThemeMode @Inject constructor(
    private val repository: SettingsRepository,
) {

    suspend operator fun invoke(themeMode: ThemeMode) = repository.setThemeMode(themeMode)
}
