package com.example.work_with_service.data.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ClientPokemonApiCreator {
    fun createPokemonApiClient(config: ClientConfig): PokemonApi =
        Retrofit.Builder()
            .baseUrl(config.baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(config.okHttpClientBuilder.build())
            .build()
            .create(PokemonApi::class.java)
}