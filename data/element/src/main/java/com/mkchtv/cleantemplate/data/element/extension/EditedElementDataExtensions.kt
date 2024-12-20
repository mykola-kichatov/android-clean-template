package com.mkchtv.cleantemplate.data.element.extension

import com.mkchtv.cleantemplate.data.element.entity.ElementEntity
import com.mkchtv.cleantemplate.domain.element.entity.EditedElementData

internal fun EditedElementData.toDbEntity(elementId: Int = 0) = ElementEntity(
    id = elementId,
    name = name,
    description = description,
    imageUrl = imageUrl,
)
