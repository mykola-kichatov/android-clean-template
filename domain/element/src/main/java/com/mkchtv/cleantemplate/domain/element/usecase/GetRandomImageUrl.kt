package com.mkchtv.cleantemplate.domain.element.usecase

import javax.inject.Inject
import kotlin.random.Random

class GetRandomImageUrl @Inject constructor() {

    operator fun invoke(): String {
        val width = 300 + Random.nextInt(0, 200)
        val height = width * 4 / 3
        return "https://picsum.photos/$width/$height"
    }
}
