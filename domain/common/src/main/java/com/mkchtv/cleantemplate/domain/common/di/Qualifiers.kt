package com.mkchtv.cleantemplate.domain.common.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppIoScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppDefaultScope
