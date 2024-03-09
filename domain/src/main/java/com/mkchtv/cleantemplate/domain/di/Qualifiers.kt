package com.mkchtv.cleantemplate.domain.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppIoScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppDefaultScope
