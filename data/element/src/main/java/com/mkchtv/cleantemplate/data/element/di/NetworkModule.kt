package com.mkchtv.cleantemplate.data.element.di

import com.mkchtv.cleantemplate.data.element.network.ElementsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val BASE_URL = "https://dummyjson.com/"

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .build()

    // The API may send fields the response classes don't declare; skip them instead of failing.
    @Singleton
    @Provides
    fun providesJson(): Json = Json { ignoreUnknownKeys = true }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideElementsService(
        retrofit: Retrofit
    ): ElementsService = retrofit.create(ElementsService::class.java)
}
