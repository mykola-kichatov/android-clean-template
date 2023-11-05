package com.mkchtv.cleantemplate.di

import com.mkchtv.cleantemplate.domain.usecase.CreateOrUpdateElement
import com.mkchtv.cleantemplate.domain.usecase.CreateOrUpdateElementUseCase
import com.mkchtv.cleantemplate.domain.usecase.DeleteElement
import com.mkchtv.cleantemplate.domain.usecase.DeleteElementUseCase
import com.mkchtv.cleantemplate.domain.usecase.GetAllElements
import com.mkchtv.cleantemplate.domain.usecase.GetAllElementsUseCase
import com.mkchtv.cleantemplate.domain.usecase.GetAuthStateFlow
import com.mkchtv.cleantemplate.domain.usecase.GetAuthStateFlowUseCase
import com.mkchtv.cleantemplate.domain.usecase.GetElement
import com.mkchtv.cleantemplate.domain.usecase.GetElementUseCase
import com.mkchtv.cleantemplate.domain.usecase.OnAuthenticated
import com.mkchtv.cleantemplate.domain.usecase.OnAuthenticatedUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun bindGetAllElementsUseCase(useCase: GetAllElements): GetAllElementsUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetElementUseCase(useCase: GetElement): GetElementUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindCreateOrUpdateElementUseCase(useCase: CreateOrUpdateElement): CreateOrUpdateElementUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindDeleteElementUseCase(useCase: DeleteElement): DeleteElementUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindGetAuthStateFlowUseCase(useCase: GetAuthStateFlow): GetAuthStateFlowUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindOnAuthenticatedUseCase(useCase: OnAuthenticated): OnAuthenticatedUseCase

}