package com.mkchtv.cleantemplate.data.auth.di

import com.mkchtv.cleantemplate.data.auth.repository.DefaultAuthRepository
import com.mkchtv.cleantemplate.domain.auth.repository.AuthRepository
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
    abstract fun bindAuthRepository(repo: DefaultAuthRepository): AuthRepository
}
