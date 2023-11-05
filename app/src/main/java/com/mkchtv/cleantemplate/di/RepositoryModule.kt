package com.mkchtv.cleantemplate.di

import com.mkchtv.cleantemplate.data.repository.DefaultAuthRepository
import com.mkchtv.cleantemplate.data.repository.DefaultElementsRepository
import com.mkchtv.cleantemplate.domain.repository.AuthRepository
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

    @Singleton
    @Binds
    abstract fun bindAuthRepository(repo: DefaultAuthRepository): AuthRepository

}