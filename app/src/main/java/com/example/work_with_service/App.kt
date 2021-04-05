package com.example.work_with_service

import android.app.Application
import com.example.work_with_service.ui.model.PagerTitlesModel
import com.example.work_with_service.ui.model.PokemonDetailModel
import com.example.work_with_service.ui.model.PokemonListModel

class App: Application() {
    val pokemonListModel = PokemonListModel()
    val pokemonDetailModel = PokemonDetailModel()
    lateinit var pagerTitlesModel: PagerTitlesModel

    override fun onCreate() {
        super.onCreate()
        pagerTitlesModel = PagerTitlesModel(mutableListOf(getString(R.string.app_name)))
    }
}