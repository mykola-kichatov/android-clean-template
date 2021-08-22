package com.mkchtv.cleantemplate.domain.base

import kotlinx.coroutines.CoroutineScope

interface UILogic {

    fun init(uiScope: CoroutineScope)

}

abstract class BaseUILogic : UILogic {

    protected lateinit var uiScope: CoroutineScope

    override fun init(uiScope: CoroutineScope) {
        this.uiScope = uiScope
    }
}