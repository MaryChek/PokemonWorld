package com.example.work_with_service.ui.pokemondetail.presenter

import com.example.work_with_service.ui.pokemondetail.contract.PokemonDetailsContract
import com.example.work_with_service.ui.model.PokemonModel
import com.example.work_with_service.ui.model.PokemonInfo
import com.example.work_with_service.ui.model.PokiAttributes

class PokemonDetailsPresenter(
    private val model: PokemonModel,
    private val view: PokemonDetailsContract.View
) : PokemonDetailsContract.Presenter {

    override fun onViewGetPokemonName(namePokemon: String) {
        model.createPokemonInfo(namePokemon, this::onPokemonInfoReadyListener)
    }

    private fun onPokemonInfoReadyListener(pokemonInfo: PokiAttributes) =
        view.showDetail(pokemonInfo as PokemonInfo)
}