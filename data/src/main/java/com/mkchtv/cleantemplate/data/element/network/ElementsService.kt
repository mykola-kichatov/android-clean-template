package com.mkchtv.cleantemplate.data.element.network

import retrofit2.http.GET

interface ElementsService {

    @GET("quotes/random")
    suspend fun getRandomElement(): ElementResponse
}
