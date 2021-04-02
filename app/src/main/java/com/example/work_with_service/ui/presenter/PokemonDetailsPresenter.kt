package com.example.work_with_service.ui.presenter

import com.example.work_with_service.ui.contract.PokemonDetailsContract
import com.example.work_with_service.ui.model.PokemonModel
import com.example.work_with_service.ui.model.PokemonInfo
import com.example.work_with_service.ui.model.PokiAttributes

class PokemonDetailsPresenter(
    private val model: PokemonModel,
    private val view: PokemonDetailsContract.View
) : PokemonDetailsContract.Presenter {
    private lateinit var pokemonName: String

    override fun onViewGetPokemonName(namePokemon: String) {
        pokemonName = namePokemon
        val pokemonInfo: PokemonInfo? = model.getPokemonDetail(namePokemon)
        if (pokemonInfo == null) {
            model.createPokemonInfo(
                pokemonName, this::onPokemonInfoReady, this::onConnectionErrorListener
            )
            view.showLoadingIndicator()
        } else {
            onPokemonInfoReady(pokemonInfo)
        }
    }

    private fun onPokemonInfoReady(pokemonInfo: PokiAttributes) {
        view.showDetail(pokemonInfo as PokemonInfo)
        view.hideLoadingIndicator()
    }

    private fun onConnectionErrorListener() {
        view.hideLoadingIndicator()
        view.showConnectionErrorMessage()
    }

    override fun onRetryConnectionClick() {
        view.hideConnectionErrorMessage()
        model.createPokemonInfo(
            pokemonName,
            this::onPokemonInfoReady,
            this::onConnectionErrorListener
        )
        view.showLoadingIndicator()
    }
}