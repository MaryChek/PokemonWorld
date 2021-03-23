package com.example.work_with_service.presenter

import com.example.work_with_service.contract.PokemonDetailsContract
import com.example.work_with_service.model.PokemonModel
import com.example.work_with_service.model.PokemonInfo
import com.example.work_with_service.model.PokiAttributes

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