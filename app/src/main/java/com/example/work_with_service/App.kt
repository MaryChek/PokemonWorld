package com.example.work_with_service

import android.app.Application
import com.example.work_with_service.data.api.ClientConfig
import com.example.work_with_service.data.api.PokemonApiTalker
import com.example.work_with_service.data.mappers.PokemonResourceMapper
import com.example.work_with_service.data.repository.PokemonRepository
import com.example.work_with_service.data.api.ClientPokemonApiCreator
import okhttp3.OkHttpClient

class App : Application() {
//    lateinit var pokemonListModel: PokemonListModel
//    lateinit var pokemonDetailModel: PokemonDetailModel
    lateinit var repository: PokemonRepository
//    lateinit var pokemonListViewModel: PokemonListViewModel
//    lateinit var pokemonDetailViewModel: PokemonDetailViewModel

    override fun onCreate() {
        super.onCreate()
        val clientConfig = ClientConfig(BASE_URL, OkHttpClient.Builder())
        val apiTalker = PokemonApiTalker(clientConfig, ClientPokemonApiCreator())
        repository = PokemonRepository(apiTalker, PokemonResourceMapper())
//        pokemonListViewModel = ViewModelProvider(this, PokemonListViewModelFactory(PokemonListMapper(), repository)).get(PokemonListViewModel::class.java)
//        pokemonListModel = PokemonListModel(PokemonListMapper(), repository)
//        pokemonDetailModel = PokemonDetailModel(PokemonDetailMapper(), repository)
    }

    companion object {
        private const val BASE_URL = "https://pokeapi.co/api/v2/"
    }
}
