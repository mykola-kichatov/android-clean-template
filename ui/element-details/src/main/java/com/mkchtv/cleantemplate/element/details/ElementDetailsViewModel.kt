package com.mkchtv.cleantemplate.element.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkchtv.cleantemplate.common.extensions.getIntOrDefault
import com.mkchtv.cleantemplate.domain.common.di.AppIoScope
import com.mkchtv.cleantemplate.domain.element.usecase.CreateElement
import com.mkchtv.cleantemplate.domain.element.usecase.DeleteElement
import com.mkchtv.cleantemplate.domain.element.usecase.ElementFlow
import com.mkchtv.cleantemplate.domain.element.usecase.UpdateElement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ElementDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @param:AppIoScope private val appIoScope: CoroutineScope,
    elementFlow: ElementFlow,
    private val createElement: CreateElement,
    private val updateElement: UpdateElement,
    private val deleteElement: DeleteElement,
) : ViewModel() {

    private val elementId = savedStateHandle.getIntOrDefault(ARG_KEY_ELEMENT_ID, NEW_ELEMENT_ID)

    val uiState: StateFlow<UiState> = elementFlow(elementId)
        .map { element -> UiState(isLoading = false, element = element) }
        .stateIn(
            initialValue = UiState(),
            scope = viewModelScope,
            started = SharingStarted.Lazily,
        )

    private val _effects = Channel<Effect>()
    val effects = _effects.receiveAsFlow()

    fun onIntent(intent: Intent) = when (intent) {
        is Intent.CreateElement -> {
            appIoScope.launch { createElement(name = intent.name, description = intent.description) }
            viewModelScope.launch { _effects.send(Effect.NavigateBack) }
        }
        is Intent.UpdateElement -> {
            appIoScope.launch { updateElement(elementId, intent.name, intent.description, intent.imageUrl) }
            viewModelScope.launch { _effects.send(Effect.NavigateBack) }
        }
        Intent.DeleteElement -> {
            appIoScope.launch { deleteElement(elementId) }
            viewModelScope.launch { _effects.send(Effect.NavigateBack) }
        }
        Intent.BackClick -> viewModelScope.launch { _effects.send(Effect.NavigateBack) }
    }
}
