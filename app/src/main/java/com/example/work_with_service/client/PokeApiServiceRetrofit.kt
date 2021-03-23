package com.example.work_with_service.client

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

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
        .client(config.okHttpConfig.build())
        .build()
        .create(PokeApiService::class.java)

}