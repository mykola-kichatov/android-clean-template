package com.mkchtv.cleantemplate.element.list

import androidx.compose.runtime.Immutable
import com.mkchtv.cleantemplate.element.list.entity.ElementItem

@Immutable
internal data class UiState(
    val isLoading: Boolean = true,
    val elements: List<ElementItem> = emptyList(),
)

internal sealed interface Intent {
    data object PullNewElement : Intent
    data class ElementClick(val itemId: Int) : Intent
    data object AddNewElement : Intent
}

internal sealed interface Effect {
    data class ShowError(val message: String) : Effect
    data class NavigateToElement(val itemId: Int) : Effect
    data object NavigateToAddElement : Effect
}
