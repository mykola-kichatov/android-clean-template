package com.mkchtv.cleantemplate.element.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkchtv.cleantemplate.common.extensions.getIntOrDefault
import com.mkchtv.cleantemplate.domain.common.di.AppIoScope
import com.mkchtv.cleantemplate.domain.element.usecase.CreateElement
import com.mkchtv.cleantemplate.domain.element.usecase.DeleteElement
import com.mkchtv.cleantemplate.domain.element.usecase.GetElement
import com.mkchtv.cleantemplate.domain.element.usecase.UpdateElement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ElementDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @AppIoScope private val appIoScope: CoroutineScope,
    getElement: GetElement,
    private val createElement: CreateElement,
    private val updateElement: UpdateElement,
    private val deleteElement: DeleteElement,
) : ViewModel() {

    private val elementId = savedStateHandle.getIntOrDefault(ARG_KEY_ELEMENT_ID, NEW_ELEMENT_ID)

    val screenState: StateFlow<ElementDetailsScreenState> = getElement(elementId)
        .map { element ->
            element?.let {
                ElementDetailsScreenState.UpdateExistedElement(it)
            } ?: ElementDetailsScreenState.CreateNewElement
        }
        .stateIn(
            initialValue = ElementDetailsScreenState.Loading,
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
        )

    fun onCreateConfirmed(
        name: String,
        description: String,
    ) = appIoScope.launch {
        createElement(name = name, description = description)
    }

    fun onUpdateConfirmed(
        name: String,
        description: String,
        imageUrl: String,
    ) = appIoScope.launch {
        updateElement(
            elementId = elementId,
            name = name,
            description = description,
            imageUrl = imageUrl,
        )
    }

    fun onDeleteConfirmed() = appIoScope.launch {
        deleteElement(elementId)
    }
}
