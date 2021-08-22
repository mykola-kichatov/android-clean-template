package com.mkchtv.cleantemplate.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mkchtv.cleantemplate.domain.base.UILogic

abstract class BaseViewModel<T : UILogic>(
    application: Application,
    protected val logic: T
) : AndroidViewModel(application) {

    init {
        logic.init(viewModelScope)
    }

}