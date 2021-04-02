package com.example.work_with_service

import android.app.Application
import com.example.work_with_service.ui.model.PagerTitlesModel
import com.example.work_with_service.ui.model.PokemonModel

class App: Application() {
    val pokemonModel = PokemonModel()
    lateinit var pagerTitlesModel: PagerTitlesModel

    override fun onCreate() {
        super.onCreate()
        pagerTitlesModel = PagerTitlesModel(mutableListOf(getString(R.string.app_name)))
    }
}