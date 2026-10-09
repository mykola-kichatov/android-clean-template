package com.mkchtv.cleantemplate.domain.settings.usecase

import com.mkchtv.cleantemplate.domain.settings.repository.SettingsRepository
import javax.inject.Inject

class SetDynamicColor @Inject constructor(
    private val repository: SettingsRepository,
) {

    suspend operator fun invoke(enabled: Boolean) = repository.setDynamicColor(enabled)
}
