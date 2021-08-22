package com.mkchtv.cleantemplate.di

import com.mkchtv.cleantemplate.common.DefaultL10N
import com.mkchtv.cleantemplate.common.DefaultUIMessageNotifier
import com.mkchtv.cleantemplate.domain.common.L10N
import com.mkchtv.cleantemplate.domain.common.UIMessageNotifier
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilModule {

    @Singleton
    @Binds
    abstract fun bindL10N(l10N: DefaultL10N): L10N

    @Singleton
    @Binds
    abstract fun bindMessageNotifier(notifier: DefaultUIMessageNotifier): UIMessageNotifier

}