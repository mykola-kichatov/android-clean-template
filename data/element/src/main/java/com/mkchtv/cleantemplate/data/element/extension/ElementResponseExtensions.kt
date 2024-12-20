package com.mkchtv.cleantemplate.data.element.extension

import com.mkchtv.cleantemplate.data.element.network.ElementResponse
import com.mkchtv.cleantemplate.domain.element.entity.PullElementData

internal fun ElementResponse.toPullData() = PullElementData(
    name = author,
    description = quote,
)
