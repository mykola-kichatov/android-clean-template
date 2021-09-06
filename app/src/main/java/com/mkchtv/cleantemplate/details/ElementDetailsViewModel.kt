package com.mkchtv.cleantemplate.details

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mkchtv.cleantemplate.base.BaseViewModel
import com.mkchtv.cleantemplate.domain.common.Constants
import com.mkchtv.cleantemplate.domain.details.ElementDetailsLogic
import com.mkchtv.cleantemplate.list.ElementItem
import com.mkchtv.cleantemplate.mapper.toUiItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ElementDetailsViewModel @Inject constructor(
    application: Application,
    logic: ElementDetailsLogic,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<ElementDetailsLogic>(application, logic) {

    private val _elementState = MutableStateFlow<ElementItem>(ElementItem.NEW)
    val elementState: StateFlow<ElementItem> = _elementState

    init {
        collectElementFlow()
    }

    private fun collectElementFlow() {
        viewModelScope.launch {
            val elementId = savedStateHandle.get<Int>(Constants.ARG_KEY_ELEMENT_ID)
                ?: Constants.NEW_ELEMENT_ID
            logic.elementFlow(elementId).map { it.toUiItem() }.collect { _elementState.value = it }
        }
    }

}