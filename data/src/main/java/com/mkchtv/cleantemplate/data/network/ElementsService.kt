package com.mkchtv.cleantemplate.data.network

import retrofit2.http.GET

interface ElementsService {

    @GET("activity")
    suspend fun getRandomElement(): ElementResponse
}
