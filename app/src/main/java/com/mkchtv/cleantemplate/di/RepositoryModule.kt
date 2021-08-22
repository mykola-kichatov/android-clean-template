package com.mkchtv.cleantemplate.di

import com.mkchtv.cleantemplate.data.repository.DefaultElementsRepository
import com.mkchtv.cleantemplate.domain.repository.ElementsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindElementsRepository(repo: DefaultElementsRepository): ElementsRepository

}