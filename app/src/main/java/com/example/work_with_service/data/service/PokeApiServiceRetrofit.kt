package com.example.work_with_service.data.service

import com.example.work_with_service.data.client.ClientConfig
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal class PokeApiServiceRetrofit(config: ClientConfig) {
    val client: PokeApiService = Retrofit.Builder()
        .baseUrl(config.baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(config.okHttpClientBuilder.build())
        .build()
        .create(PokeApiService::class.java)
}