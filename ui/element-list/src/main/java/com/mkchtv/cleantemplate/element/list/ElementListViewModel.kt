package com.mkchtv.cleantemplate.element.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkchtv.cleantemplate.common.extensions.toMessageRes
import com.mkchtv.cleantemplate.domain.element.usecase.ElementsFlow
import com.mkchtv.cleantemplate.domain.element.usecase.PullElement
import com.mkchtv.cleantemplate.element.list.Effect.NavigateToAddElement
import com.mkchtv.cleantemplate.element.list.Effect.NavigateToElement
import com.mkchtv.cleantemplate.element.list.Effect.ShowError
import com.mkchtv.cleantemplate.element.list.entity.toUiItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@ExperimentalCoroutinesApi
@HiltViewModel
internal class ElementListViewModel @Inject constructor(
    elementsFlow: ElementsFlow,
    private val pullElement: PullElement,
) : ViewModel() {

    val uiState: StateFlow<UiState> = elementsFlow()
        .map { elements ->
            UiState(isLoading = false, elements = elements.toUiItems())
        }
        .stateIn(
            initialValue = UiState(),
            scope = viewModelScope,
            started = SharingStarted.Lazily,
        )

    private val _effects = Channel<Effect>()
    val effects = _effects.receiveAsFlow()

    fun onIntent(intent: Intent) = when (intent) {
        Intent.PullNewElement -> pullNewElement()
        is Intent.ElementClick -> viewModelScope.launch {
            _effects.send(NavigateToElement(intent.itemId))
        }

        Intent.AddNewElement -> viewModelScope.launch {
            _effects.send(NavigateToAddElement)
        }
    }

    private fun pullNewElement() = viewModelScope.launch {
        try {
            pullElement()
        } catch (e: CancellationException) {
            // not an error: the user left, so let the coroutine stop
            throw e
        } catch (e: Exception) {
            _effects.send(ShowError(e.toMessageRes()))
        }
    }
}
