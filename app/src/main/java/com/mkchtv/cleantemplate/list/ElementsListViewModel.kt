package com.mkchtv.cleantemplate.list

import android.app.Application
import com.mkchtv.cleantemplate.base.BaseViewModel
import com.mkchtv.cleantemplate.domain.list.ElementsListLogic
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ElementsListViewModel @Inject constructor(
    application: Application,
    logic: ElementsListLogic,
) : BaseViewModel<ElementsListLogic>(application, logic) {


}