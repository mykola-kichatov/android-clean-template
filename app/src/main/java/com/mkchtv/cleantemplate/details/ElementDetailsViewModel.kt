package com.mkchtv.cleantemplate.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkchtv.cleantemplate.domain.common.Constants
import com.mkchtv.cleantemplate.domain.di.AppIoScope
import com.mkchtv.cleantemplate.domain.usecase.CreateOrUpdateElementUseCase
import com.mkchtv.cleantemplate.domain.usecase.DeleteElementUseCase
import com.mkchtv.cleantemplate.domain.usecase.GetElementUseCase
import com.mkchtv.cleantemplate.list.ElementItem
import com.mkchtv.cleantemplate.mapper.toDomain
import com.mkchtv.cleantemplate.util.getIntOrDefault
import com.mkchtv.cleantemplate.util.getStringOrDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ElementDetailsViewModel @Inject constructor(
    private val getElement: GetElementUseCase,
    private val createOrUpdateElement: CreateOrUpdateElementUseCase,
    private val deleteElement: DeleteElementUseCase,
    @AppIoScope private val appIoScope: CoroutineScope,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _elementState = MutableStateFlow(makeInitialState())
    val elementState = _elementState.asStateFlow()

    init {
        collectElementFlow()
    }

    fun onCreateUpdateConfirmed() = appIoScope.launch {
        createOrUpdateElement(_elementState.value.toDomain())
    }

    fun onDeleteConfirmed() = appIoScope.launch {
        deleteElement(_elementState.value.toDomain())
    }

    fun onNameTextChanged(text: String) {
        _elementState.update { it.copy(name = text) }
        savedStateHandle[STATE_KEY_NAME] = text
    }

    fun onDescriptionTextChanged(text: String) {
        _elementState.update { it.copy(description = text) }
        savedStateHandle[STATE_KEY_DESCRIPTION] = text
    }

    private fun makeInitialState() =
        ElementItem(
            id = getElementId(),
            name = getSavedStateName(),
            description = getSavedStateDescription()
        )

    private fun getElementId() =
        savedStateHandle.getIntOrDefault(Constants.ARG_KEY_ELEMENT_ID, Constants.NEW_ELEMENT_ID)

    private fun getSavedStateName() =
        savedStateHandle.getStringOrDefault(STATE_KEY_NAME, ElementItem.NEW.name)

    private fun getSavedStateDescription() = savedStateHandle.getStringOrDefault(
        STATE_KEY_DESCRIPTION,
        ElementItem.NEW.description
    )

    private fun collectElementFlow() =
        viewModelScope.launch {
            getElement(getElementId()).collect { element ->
                if (alreadyHaveSavedStateData())
                    return@collect
                _elementState.update {
                    it.copy(
                        id = element.id,
                        name = element.name,
                        description = element.description
                    )
                }
            }
        }

    private fun alreadyHaveSavedStateData(): Boolean = getSavedStateName().isNotEmpty()
            && getSavedStateDescription().isNotEmpty()
}

private const val STATE_KEY_NAME = "name_state"
private const val STATE_KEY_DESCRIPTION = "description_state"