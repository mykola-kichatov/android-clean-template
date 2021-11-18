package com.mkchtv.cleantemplate.list

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.mkchtv.cleantemplate.base.BaseViewModel
import com.mkchtv.cleantemplate.domain.list.ElementsListLogic
import com.mkchtv.cleantemplate.mapper.toUiItemsList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ElementsListViewModel @Inject constructor(
    application: Application,
    logic: ElementsListLogic,
) : BaseViewModel<ElementsListLogic>(application, logic) {

    val elementsState: StateFlow<List<ElementItem>> = logic.elementsFlow()
        .map { it.toUiItemsList() }
        .catch { TODO("Flow throws some exception") }
        .stateIn(
            initialValue = emptyList(),
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000)
        )

}