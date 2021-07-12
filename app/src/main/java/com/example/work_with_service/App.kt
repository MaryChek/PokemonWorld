package com.example.work_with_service

import android.app.Application
import com.example.work_with_service.data.api.ClientConfig
import com.example.work_with_service.data.api.PokemonApiTalker
import com.example.work_with_service.data.mappers.PokemonResourceMapper
import com.example.work_with_service.data.repository.PokemonRepository
import com.example.work_with_service.data.api.ClientPokemonApiCreator
import com.example.work_with_service.domain.interactor.PokemonInteractor
import com.example.work_with_service.presentation.mappers.PokemonDetailMapper
import com.example.work_with_service.presentation.mappers.PokemonListMapper
import com.example.work_with_service.presentation.viewmodels.factory.PokemonViewModelFactory
import okhttp3.OkHttpClient

class App : Application() {

    lateinit var viewModelFactory: PokemonViewModelFactory

    override fun onCreate() {
        super.onCreate()
        val clientConfig = ClientConfig(BASE_URL, OkHttpClient.Builder())
        val apiTalker = PokemonApiTalker(clientConfig, ClientPokemonApiCreator())
        val repository = PokemonRepository(apiTalker, PokemonResourceMapper())
        val interactor = PokemonInteractor(repository)
        viewModelFactory = PokemonViewModelFactory(
            interactor, PokemonListMapper(), PokemonDetailMapper()
        )
    }

    companion object {
        private const val BASE_URL = "https://pokeapi.co/api/v2/"
    }
}
