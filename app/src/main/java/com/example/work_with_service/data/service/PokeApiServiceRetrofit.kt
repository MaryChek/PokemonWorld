package com.example.work_with_service.data.service

import com.example.work_with_service.data.client.ClientConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class PokeApiServiceRetrofit(
    config: ClientConfig
) {
    val client: PokeApiService = Retrofit.Builder()
        .baseUrl(config.rootUrl)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().apply {
                    setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                }.create()
            )
        )
        .client(config.getUnsafeOkHttpClientBuilder().build())
        .build()
        .create(PokeApiService::class.java)

}