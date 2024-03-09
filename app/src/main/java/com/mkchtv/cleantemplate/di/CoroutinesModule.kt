package com.mkchtv.cleantemplate.di

import com.mkchtv.cleantemplate.domain.di.AppDefaultScope
import com.mkchtv.cleantemplate.domain.di.AppIoScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {

    @Provides
    @Singleton
    @AppIoScope
    fun provideAppCoroutineScopeIO(handler: CoroutineExceptionHandler) =
        CoroutineScope(SupervisorJob() + Dispatchers.IO + handler + CoroutineName("app_io_scope"))

    @Provides
    @Singleton
    @AppDefaultScope
    fun provideAppCoroutineScopeDefault(handler: CoroutineExceptionHandler) = CoroutineScope(
        SupervisorJob() + Dispatchers.Default + handler + CoroutineName("app_default_scope")
    )

    @Provides
    @Singleton
    fun provideAppExceptionHandler() = CoroutineExceptionHandler { _, exception ->
        println("App exception handler got $exception")
    }
}
