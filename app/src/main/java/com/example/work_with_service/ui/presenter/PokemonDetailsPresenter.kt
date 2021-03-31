package com.example.work_with_service.ui.presenter

import com.example.work_with_service.ui.contract.PokemonDetailsContract
import com.example.work_with_service.ui.model.PokemonModel
import com.example.work_with_service.ui.model.PokemonInfo
import com.example.work_with_service.ui.model.PokiAttributes

class PokemonDetailsPresenter(
    private val model: PokemonModel,
    private val view: PokemonDetailsContract.View
) : PokemonDetailsContract.Presenter {
    private var pokemonName: String? = null

    override fun onViewGetPokemonName(namePokemon: String) {
        pokemonName = namePokemon
        model.createPokemonInfo(namePokemon, this::onPokemonInfoReady)
    }

    override fun onViewRestart() {
        val pokemonInfo: PokemonInfo? = model.getPokemonDetail()
        if (pokemonInfo == null) {
            pokemonName?.let {
                model.createPokemonInfo(it, this::onPokemonInfoReady)
            }
        } else {
            onPokemonInfoReady(pokemonInfo)
        }
    }

    private fun onPokemonInfoReady(pokemonInfo: PokiAttributes) =
        view.showDetail(pokemonInfo as PokemonInfo)
}