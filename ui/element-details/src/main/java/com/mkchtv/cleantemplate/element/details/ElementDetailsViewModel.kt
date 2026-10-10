package com.mkchtv.cleantemplate.element.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkchtv.cleantemplate.common.extensions.getIntOrDefault
import com.mkchtv.cleantemplate.common.extensions.toMessageRes
import com.mkchtv.cleantemplate.domain.common.di.AppIoScope
import com.mkchtv.cleantemplate.domain.element.usecase.CreateElement
import com.mkchtv.cleantemplate.domain.element.usecase.DeleteElement
import com.mkchtv.cleantemplate.domain.element.usecase.ElementFlow
import com.mkchtv.cleantemplate.domain.element.usecase.UpdateElement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

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

    private val isSaving = MutableStateFlow(false)

    val uiState: StateFlow<UiState> = combine(elementFlow(elementId), isSaving) { element, saving ->
        UiState(isLoading = false, element = element, isSaving = saving)
    }.stateIn(
        initialValue = UiState(),
        scope = viewModelScope,
        started = SharingStarted.Lazily,
    )

    private val _effects = Channel<Effect>()
    val effects = _effects.receiveAsFlow()

    fun onIntent(intent: Intent) = when (intent) {
        is Intent.CreateElement -> closeAfter {
            createElement(name = intent.name, description = intent.description)
        }
        is Intent.UpdateElement -> closeAfter {
            updateElement(elementId, intent.name, intent.description, intent.imageUrl)
        }
        Intent.DeleteElement -> closeAfter { deleteElement(elementId) }
        Intent.BackClick -> viewModelScope.launch { _effects.send(Effect.NavigateBack) }
    }

    /**
     * Runs [write] on the app scope, so it finishes even if the user leaves, and closes the screen
     * once it worked. If it fails, the screen stays open with the user's input and shows the error.
     */
    private fun closeAfter(write: suspend () -> Unit) = viewModelScope.launch {
        // one write at a time: the button is disabled while saving, but the keyboard can submit too
        if (!isSaving.compareAndSet(expect = false, update = true)) return@launch
        try {
            appIoScope.async { write() }.await()
            // still saving: the screen is closing, so no second write may start
            _effects.send(Effect.NavigateBack)
        } catch (e: CancellationException) {
            // not an error: the user left, and the write goes on in the app scope
            throw e
        } catch (e: Exception) {
            isSaving.value = false
            _effects.send(Effect.ShowError(e.toMessageRes()))
        }
    }
}
