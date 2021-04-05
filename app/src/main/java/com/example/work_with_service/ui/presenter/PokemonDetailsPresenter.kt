package com.example.work_with_service.ui.presenter

import com.example.work_with_service.data.entities.Pokemon
import com.example.work_with_service.ui.contract.PokemonDetailsContract
import com.example.work_with_service.ui.model.PokemonDetailModel
import com.example.work_with_service.ui.model.pokiattributes.PokemonDetail
import com.example.work_with_service.ui.model.pokiattributes.PokiAttributes

class PokemonDetailsPresenter(
    private val model: PokemonDetailModel,
    private val view: PokemonDetailsContract.View
) : PokemonDetailsContract.Presenter {
    private lateinit var pokemon: Pokemon

    override fun onViewGetPokemonName(pokemon: Pokemon) {
        this.pokemon = pokemon
        val pokemonDetail: PokemonDetail? = model.getPokemonDetail(pokemon)
        if (pokemonDetail == null) {
            model.fetchPokemonDetail(
                pokemon, this::onPokemonInfoReady, this::onConnectionErrorListener
            )
            view.showLoadingIndicator()
        } else {
            onPokemonInfoReady(pokemonDetail)
        }
    }

    private fun onPokemonInfoReady(pokemonInfo: PokiAttributes) {
        view.showDetail(pokemonInfo as PokemonDetail)
        view.hideLoadingIndicator()
    }

    private fun onConnectionErrorListener() {
        view.hideLoadingIndicator()
        view.showConnectionErrorMessage()
    }

    override fun onRetryConnectionClick() {
        view.hideConnectionErrorMessage()
        model.fetchPokemonDetail(
            pokemon,
            this::onPokemonInfoReady,
            this::onConnectionErrorListener
        )
        view.showLoadingIndicator()
    }
}