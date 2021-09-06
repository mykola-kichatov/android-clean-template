package com.mkchtv.cleantemplate.di

import com.mkchtv.cleantemplate.domain.details.DefaultElementDetailsLogic
import com.mkchtv.cleantemplate.domain.details.ElementDetailsLogic
import com.mkchtv.cleantemplate.domain.list.DefaultElementsListLogic
import com.mkchtv.cleantemplate.domain.list.ElementsListLogic
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class LogicModule {

    @Binds
    @ViewModelScoped
    abstract fun bindElementsListLogic(logic: DefaultElementsListLogic): ElementsListLogic

    @Binds
    @ViewModelScoped
    abstract fun bindElementDetailsLogic(logic: DefaultElementDetailsLogic): ElementDetailsLogic

}