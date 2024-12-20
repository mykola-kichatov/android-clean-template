package com.mkchtv.cleantemplate.domain.element.extension

import com.mkchtv.cleantemplate.domain.element.entity.EditedElementData
import com.mkchtv.cleantemplate.domain.element.entity.PullElementData

fun PullElementData.toEditedData(imageUrl: String) = EditedElementData(
    name = name,
    description = description,
    imageUrl = imageUrl,
)
