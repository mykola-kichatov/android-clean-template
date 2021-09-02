package com.mkchtv.cleantemplate.list

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.mkchtv.cleantemplate.base.BaseViewModel
import com.mkchtv.cleantemplate.domain.list.ElementsListLogic
import com.mkchtv.cleantemplate.mapper.toUiItemsList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ElementsListViewModel @Inject constructor(
    application: Application,
    logic: ElementsListLogic,
) : BaseViewModel<ElementsListLogic>(application, logic) {

    private val _elementsState = MutableStateFlow<List<ElementItem>>(emptyList())
    val elementsState: StateFlow<List<ElementItem>> = _elementsState

    init {
        collectElementsFlow()
    }

    private fun collectElementsFlow() {
        viewModelScope.launch {
            logic.elementsFlow().map { it.toUiItemsList() }.collect { _elementsState.value = it }
        }
    }

}