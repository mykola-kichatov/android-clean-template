package com.mkchtv.cleantemplate.element.details

import androidx.compose.runtime.Immutable
import com.mkchtv.cleantemplate.domain.element.entity.Element

@Immutable
internal data class UiState(
    val isLoading: Boolean = true,
    val element: Element? = null,
)

internal sealed interface Intent {
    data class CreateElement(val name: String, val description: String) : Intent
    data class UpdateElement(val name: String, val description: String, val imageUrl: String) : Intent
    data object DeleteElement : Intent
    data object BackClick : Intent
}

internal sealed interface Effect {
    data object NavigateBack : Effect
}
