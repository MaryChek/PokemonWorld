package com.example.work_with_service.ui.presenter

import com.example.work_with_service.ui.model.Pokemon
import com.example.work_with_service.ui.contract.PokemonDetailsContract
import com.example.work_with_service.ui.model.PokemonDetail
import com.example.work_with_service.ui.model.PokemonDetailModel

class PokemonDetailsPresenter(
    private val model: PokemonDetailModel,
    private val view: PokemonDetailsContract.View
) : PokemonDetailsContract.Presenter {
    private lateinit var pokemon: Pokemon

    override fun init(pokemon: Pokemon) {
        this.pokemon = pokemon
        val pokemonDetail: PokemonDetail? = model.getPokemonDetail(pokemon)
        if (pokemonDetail == null) {
            fetchPokemonDetail()
        } else {
            onPokemonInfoReady(pokemonDetail)
        }
    }

    private fun fetchPokemonDetail() {
        model.fetchPokemonDetail(pokemon, this::onPokemonInfoReady, this::onConnectionErrorListener)
        view.showLoadingIndicator()
    }

    private fun onPokemonInfoReady(pokemonDetail: PokemonDetail) {
        view.showPokemonMain(pokemonDetail, pokemon)
        view.showPokemonAbilities(pokemonDetail.abilities)
        view.showPokemonTypes(pokemonDetail.types)
        view.hideLoadingIndicator()
    }

    private fun onConnectionErrorListener() {
        view.hideLoadingIndicator()
        view.showConnectionErrorMessage()
    }

    override fun onRetryConnectionClick() {
        view.hideConnectionErrorMessage()
        fetchPokemonDetail()
    }
}