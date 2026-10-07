package com.mkchtv.cleantemplate.domain.element.usecase

import com.mkchtv.cleantemplate.domain.element.repository.ImageUrlProvider
import javax.inject.Inject

class GetRandomImageUrl @Inject constructor(
    private val imageUrlProvider: ImageUrlProvider,
) {

    operator fun invoke(): String = imageUrlProvider.randomImageUrl()
}
