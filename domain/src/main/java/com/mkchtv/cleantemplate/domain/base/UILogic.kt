package com.mkchtv.cleantemplate.domain.base

import kotlinx.coroutines.CoroutineScope

interface UILogic {

    fun onUIInit(uiScope: CoroutineScope)

}

abstract class BaseUILogic : UILogic {

    protected lateinit var uiScope: CoroutineScope

    override fun onUIInit(uiScope: CoroutineScope) {
        this.uiScope = uiScope
    }

}