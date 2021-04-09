package com.example.work_with_service.ui.model

import com.example.work_with_service.data.model.Pokemon
import com.example.work_with_service.data.service.PokemonService
import com.example.work_with_service.ui.mapper.PokemonListMapper
import com.example.work_with_service.ui.model.pokiattributes.ListPokemonAttributes
import com.example.work_with_service.ui.model.pokiattributes.PokiAttributes
import com.example.work_with_service.ui.model.servicepokemonanswer.ListPokemon

class PokemonListModel: PokemonListMapper() {
    private var onListReadyListener: ((PokiAttributes) -> Unit)? = null
    private var pokemonService: PokemonService = PokemonService()
    private var pokemonList: List<Pokemon> = listOf()

    fun fetchPokemonList(
        onPokemonListReadyListener: (PokiAttributes) -> Unit,
        onServiceReturnError: () -> Unit
    ) {
        onListReadyListener = onPokemonListReadyListener
        pokemonService.callPokemonSource(this::onServiceFinishedWork, onServiceReturnError)
    }

    fun isPokemonListAttributesEmpty(): Boolean =
        pokemonList.isEmpty()

    fun getListPokemonAttributes(): ListPokemonAttributes =
        ListPokemonAttributes(map(pokemonList))

    fun getPokemonByName(namePokemon: String): Pokemon =
        pokemonList.first {
            it.name.equals(namePokemon, true)
        }

    private fun onServiceFinishedWork(pokemonAnswer: ListPokemon) {
        pokemonList = pokemonAnswer.listPokemon
        onListReadyListener?.invoke(ListPokemonAttributes(map(pokemonList)))
    }
}
