package com.mkchtv.cleantemplate.data.element.di

import com.mkchtv.cleantemplate.data.element.repository.DefaultElementsRepository
import com.mkchtv.cleantemplate.domain.element.repository.ElementsRepository
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
    abstract fun bindElementsRepository(repo: DefaultElementsRepository): ElementsRepository
}
