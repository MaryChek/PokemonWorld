package com.example.work_with_service.presentation.presenter

import com.example.work_with_service.presentation.contract.PokemonListContract
import com.example.work_with_service.presentation.models.Pokemon
import com.example.work_with_service.presentation.models.PokemonListModel

class PokemonListPresenter(
    private val model: PokemonListModel,
    private val view: PokemonListContract.View
) : PokemonListContract.Presenter {

    override fun onViewCreated() =
//        if (model.isPokemonListEmpty()) {
            fetchPokemonList()
//        } else {
//            view.hideLoadingIndicator()
//            view.updatePokemonList(model.getListPokemon())
//        }

    private fun fetchPokemonList() {
//        model.fetchPokemonList(this::onPokemonListReadyListener, this::onConnectionErrorListener)
//        view.showLoadingIndicator()
    }

    override fun onItemPokemonClick(namePokemon: String) =
        fetchPokemonList()
//        view.openDetailedPage(model.getPokemonByName(namePokemon))

    private fun onPokemonListReadyListener(pokemons: List<Pokemon>) {
        view.updatePokemonList(pokemons)
        view.hideLoadingIndicator()
    }

    private fun onConnectionErrorListener() {
        view.hideLoadingIndicator()
        view.showConnectionErrorMessage()
    }

    override fun onRetryConnectionClick() {
        view.hideConnectionErrorMessage()
        fetchPokemonList()
    }
}