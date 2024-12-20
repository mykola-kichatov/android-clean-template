package com.mkchtv.cleantemplate.data.element.network

import retrofit2.http.GET

internal interface ElementsService {

    @GET("quotes/random")
    suspend fun getRandomElement(): ElementResponse
}
