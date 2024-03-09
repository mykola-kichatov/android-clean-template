package com.mkchtv.cleantemplate.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkchtv.cleantemplate.domain.common.Constants
import com.mkchtv.cleantemplate.domain.di.AppIoScope
import com.mkchtv.cleantemplate.domain.usecase.CreateOrUpdateElement
import com.mkchtv.cleantemplate.domain.usecase.DeleteElement
import com.mkchtv.cleantemplate.domain.usecase.GetElement
import com.mkchtv.cleantemplate.list.ElementItem
import com.mkchtv.cleantemplate.mapper.toUiItem
import com.mkchtv.cleantemplate.util.getIntOrDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ElementDetailsViewModel @Inject constructor(
    getElement: GetElement,
    private val createOrUpdateElement: CreateOrUpdateElement,
    private val deleteElement: DeleteElement,
    @AppIoScope private val appIoScope: CoroutineScope,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val elementState: StateFlow<ElementItem> = getElement(getElementId())
        .map { it.toUiItem() }
        .catch { TODO("Flow throws some exception") }
        .stateIn(
            initialValue = ElementItem.NEW,
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000)
        )

    fun onCreateUpdateConfirmed(
        name: String,
        description: String
    ) = appIoScope.launch {
        createOrUpdateElement(id = getElementId(), name = name, description = description)
    }

    fun onDeleteConfirmed() = appIoScope.launch {
        deleteElement(getElementId())
    }

    private fun getElementId() =
        savedStateHandle.getIntOrDefault(Constants.ARG_KEY_ELEMENT_ID, Constants.NEW_ELEMENT_ID)
}
