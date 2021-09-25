package com.mkchtv.cleantemplate.details

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mkchtv.cleantemplate.base.BaseViewModel
import com.mkchtv.cleantemplate.domain.common.Constants
import com.mkchtv.cleantemplate.domain.details.ElementDetailsLogic
import com.mkchtv.cleantemplate.list.ElementItem
import com.mkchtv.cleantemplate.mapper.toDomain
import com.mkchtv.cleantemplate.util.getIntOrDefault
import com.mkchtv.cleantemplate.util.getStringOrDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ElementDetailsViewModel @Inject constructor(
    application: Application,
    logic: ElementDetailsLogic,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<ElementDetailsLogic>(application, logic) {

    private val _elementState = MutableStateFlow(makeInitialState())
    val elementState = _elementState.asStateFlow()

    init {
        collectElementFlow()
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
            logic.elementFlow(getElementId()).collect { element ->
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

    fun onCreateUpdateClick() = logic.onCreateUpdateConfirmed(_elementState.value.toDomain())

    fun onNameTextChanged(text: String) {
        _elementState.update { it.copy(name = text) }
        savedStateHandle.set(STATE_KEY_NAME, text)
    }

    fun onDescriptionTextChanged(text: String) {
        _elementState.update { it.copy(description = text) }
        savedStateHandle.set(STATE_KEY_DESCRIPTION, text)
    }

}

private const val STATE_KEY_NAME = "name_state"
private const val STATE_KEY_DESCRIPTION = "description_state"