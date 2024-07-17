package com.mkchtv.cleantemplate.element.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkchtv.cleantemplate.common.extensions.getIntOrDefault
import com.mkchtv.cleantemplate.domain.common.Constants
import com.mkchtv.cleantemplate.domain.common.di.AppIoScope
import com.mkchtv.cleantemplate.domain.element.usecase.CreateOrUpdateElement
import com.mkchtv.cleantemplate.domain.element.usecase.DeleteElement
import com.mkchtv.cleantemplate.domain.element.usecase.GetElement
import com.mkchtv.cleantemplate.element.entity.ElementItem
import com.mkchtv.cleantemplate.element.mapper.toUiItem
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
    private val createOrUpdateElement: CreateOrUpdateElement,
    private val deleteElement: DeleteElement,
) : ViewModel() {

    private val elementId =
        savedStateHandle.getIntOrDefault(ARG_KEY_ELEMENT_ID, Constants.NEW_ELEMENT_ID)

    val elementState: StateFlow<ElementItem> = getElement(elementId)
        .map { it.toUiItem() }
        .stateIn(
            initialValue = ElementItem.NEW,
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
        )

    fun onCreateUpdateConfirmed(
        name: String,
        description: String,
    ) = appIoScope.launch {
        createOrUpdateElement(id = elementId, name = name, description = description)
    }

    fun onDeleteConfirmed() = appIoScope.launch {
        deleteElement(elementId)
    }
}
