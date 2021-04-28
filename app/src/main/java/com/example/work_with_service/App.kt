package com.example.work_with_service

import android.app.Application
import com.example.work_with_service.data.client.ClientConfig
import com.example.work_with_service.data.client.PokeApiClient
import com.example.work_with_service.data.mapper.PokemonResourceMapper
import com.example.work_with_service.data.repository.PokemonRepository
import com.example.work_with_service.data.service.PokeApiServiceRetrofit
import com.example.work_with_service.ui.mapper.PokemonDetailMapper
import com.example.work_with_service.ui.mapper.PokemonListMapper
import com.example.work_with_service.ui.model.PokemonDetailModel
import com.example.work_with_service.ui.model.PokemonListModel
import okhttp3.OkHttpClient

class App : Application() {
    lateinit var pokemonListModel: PokemonListModel
    lateinit var pokemonDetailModel: PokemonDetailModel

    override fun onCreate() {
        super.onCreate()
        val clientConfig = ClientConfig(BASE_URL, OkHttpClient.Builder())
        val pokiClient = PokeApiClient(clientConfig, PokeApiServiceRetrofit())
        val repository = PokemonRepository(pokiClient, PokemonResourceMapper())
        pokemonListModel = PokemonListModel(PokemonListMapper(), repository)
        pokemonDetailModel = PokemonDetailModel(PokemonDetailMapper(), repository)
    }

    companion object {
        private const val BASE_URL = "https://pokeapi.co/api/v2/"
    }
}
