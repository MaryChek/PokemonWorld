package com.example.work_with_service

import android.app.Application
import com.example.work_with_service.model.PokemonModel

class App: Application() {
    lateinit var pokemonModel: PokemonModel
        private set

    override fun onCreate() {
        super.onCreate()
        pokemonModel = PokemonModel()
    }
}