package com.example.work_with_service.ui.presenter

import com.example.work_with_service.ui.contract.PokemonListContract
import com.example.work_with_service.ui.model.PokemonListModel
import com.example.work_with_service.ui.model.pokiattributes.ListPokemonAttributes
import com.example.work_with_service.ui.model.pokiattributes.PokiAttributes

class PokemonListPresenter(
    private val model: PokemonListModel,
    private val view: PokemonListContract.View
) : PokemonListContract.Presenter {

    override fun onViewCreated() {
        if (model.isPokemonListAttributesEmpty()) {
            fetchPokemonList()
        } else {
            view.hideLoadingIndicator()
            view.updatePokemonList(model.getListPokemonAttributes().listAttributes)
        }
    }

    private fun fetchPokemonList() {
        model.fetchPokemonList(this::onPokemonListReadyListener, this::onConnectionErrorListener)
        view.showLoadingIndicator()
    }

    override fun onItemPokemonClick(namePokemon: String) =
        view.openDetailedPage(model.getPokemonByName(namePokemon))

    private fun onPokemonListReadyListener(listPokemonAttributes: PokiAttributes) {
        view.updatePokemonList((listPokemonAttributes as ListPokemonAttributes).listAttributes)
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