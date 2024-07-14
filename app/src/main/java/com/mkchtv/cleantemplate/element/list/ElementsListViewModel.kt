package com.mkchtv.cleantemplate.element.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkchtv.cleantemplate.domain.element.usecase.GetAllElements
import com.mkchtv.cleantemplate.domain.element.usecase.PullNewElement
import com.mkchtv.cleantemplate.element.mapper.toUiItemsList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ElementsListViewModel @Inject constructor(
    getAllElements: GetAllElements,
    private val pullNewElement: PullNewElement,
) : ViewModel() {

    val elementsState: StateFlow<List<ElementItem>> = getAllElements()
        .map { it.toUiItemsList() }
        .catch { TODO("Flow throws some exception") }
        .stateIn(
            initialValue = emptyList(),
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000)
        )

    fun onPullNewElementRequested() = viewModelScope.launch {
        runCatching { pullNewElement() }
            .onFailure {
                // TODO
            }
    }
}
