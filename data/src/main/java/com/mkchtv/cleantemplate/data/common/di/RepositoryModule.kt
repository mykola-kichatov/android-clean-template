package com.mkchtv.cleantemplate.data.common.di

import com.mkchtv.cleantemplate.data.auth.repository.DefaultAuthRepository
import com.mkchtv.cleantemplate.data.element.repository.DefaultElementsRepository
import com.mkchtv.cleantemplate.domain.auth.repository.AuthRepository
import com.mkchtv.cleantemplate.domain.element.repository.ElementsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindElementsRepository(repo: DefaultElementsRepository): ElementsRepository

    @Singleton
    @Binds
    abstract fun bindAuthRepository(repo: DefaultAuthRepository): AuthRepository
}
