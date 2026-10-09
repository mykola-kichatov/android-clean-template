package com.mkchtv.cleantemplate.domain.settings.usecase

import com.mkchtv.cleantemplate.domain.settings.entity.Appearance
import com.mkchtv.cleantemplate.domain.settings.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveAppearance @Inject constructor(
    private val repository: SettingsRepository,
) {

    operator fun invoke(): Flow<Appearance> = repository.appearance
}
