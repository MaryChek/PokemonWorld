package com.example.work_with_service.presenter

import com.example.work_with_service.contract.PokemonListContract
import com.example.work_with_service.model.PokemonModel
import com.example.work_with_service.model.ListPokemonAttributes
import com.example.work_with_service.model.PokiAttributes

class PokemonListPresenter(
    private val model: PokemonModel,
    private val view: PokemonListContract.View
) : PokemonListContract.Presenter {

    override fun onViewCreated() {
        model.createPokemonList(this::onPokemonListReadyListener)
    }

    override fun onViewRestart() {
        if (model.isPokemonListAttributesEmpty()) {
            model.createPokemonList(this::onPokemonListReadyListener)
        } else {
            view.updatePokemonList(model.getListPokemonAttributes().listAttributes)
        }
    }

    override fun onItemPokemonClick(namePokemon: String) {
        view.openDetailedPage(namePokemon)
    }

    private fun onPokemonListReadyListener(listPokemonAttributes: PokiAttributes) {
        view.updatePokemonList((listPokemonAttributes as ListPokemonAttributes).listAttributes)
    }
}