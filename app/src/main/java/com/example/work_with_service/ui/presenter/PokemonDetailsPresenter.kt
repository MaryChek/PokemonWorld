package com.example.work_with_service.ui.presenter

import com.example.work_with_service.ui.contract.PokemonDetailsContract
import com.example.work_with_service.ui.model.PokemonAttributes
import com.example.work_with_service.ui.model.PokemonModel
import com.example.work_with_service.ui.model.PokemonDetail
import com.example.work_with_service.ui.model.PokiAttributes

class PokemonDetailsPresenter(
    private val model: PokemonModel,
    private val view: PokemonDetailsContract.View
) : PokemonDetailsContract.Presenter {

    override fun onViewGetPokemonAttributes(pokemonAttributes: PokemonAttributes) = Unit
//        model.createPokemonInfo(pokemonAttributes, this::onPokemonInfoReadyListener)

    private fun onPokemonInfoReadyListener(pokemonInfo: PokiAttributes) =
        view.showDetail(pokemonInfo as PokemonDetail)
}