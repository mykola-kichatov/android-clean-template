package com.mkchtv.cleantemplate.element.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkchtv.cleantemplate.domain.element.usecase.GetAllElements
import com.mkchtv.cleantemplate.domain.element.usecase.PullElement
import com.mkchtv.cleantemplate.element.list.entity.ElementItem
import com.mkchtv.cleantemplate.element.list.mapper.toUiItemsList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ElementsListViewModel @Inject constructor(
    getAllElements: GetAllElements,
    private val pullElement: PullElement,
) : ViewModel() {

    val elements: StateFlow<List<ElementItem>?> = getAllElements()
        .map {
            it.toUiItemsList()
        }
        .stateIn(
            initialValue = null,
            scope = viewModelScope,
            started = SharingStarted.Lazily
        )

    fun onPullNewElementRequested() = viewModelScope.launch {
        runCatching { pullElement() }
            .onFailure {
                // TODO
            }
    }
}
