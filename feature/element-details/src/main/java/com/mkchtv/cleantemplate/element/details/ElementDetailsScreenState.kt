package com.mkchtv.cleantemplate.element.details

import com.mkchtv.cleantemplate.domain.element.entity.Element

internal sealed class ElementDetailsScreenState {

    data object Loading : ElementDetailsScreenState()

    data object CreateNewElement : ElementDetailsScreenState()

    data class UpdateExistedElement(
        val element: Element,
    ) : ElementDetailsScreenState()
}
