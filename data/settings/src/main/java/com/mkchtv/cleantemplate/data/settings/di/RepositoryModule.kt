package com.mkchtv.cleantemplate.data.settings.di

import com.mkchtv.cleantemplate.data.settings.repository.DefaultSettingsRepository
import com.mkchtv.cleantemplate.domain.settings.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
internal abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindSettingsRepository(repo: DefaultSettingsRepository): SettingsRepository
}
