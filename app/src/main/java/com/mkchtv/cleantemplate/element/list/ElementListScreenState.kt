package com.mkchtv.cleantemplate.element.list

import com.mkchtv.cleantemplate.element.entity.ElementItem

sealed class ElementListScreenState {

    data object Loading : ElementListScreenState()

    data class ListLoaded(
        val elements: List<ElementItem>,
    ) : ElementListScreenState()
}
