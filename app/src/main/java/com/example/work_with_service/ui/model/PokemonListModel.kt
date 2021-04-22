package com.example.work_with_service.ui.model

import com.example.work_with_service.data.repository.PokemonRepository
import com.example.work_with_service.data.model.ListPokemon
import com.example.work_with_service.ui.mapper.PokemonListMapper

class PokemonListModel : PokemonListMapper() {
    private var onListReadyListener: ((PokemonsAttributes) -> Unit)? = null
    private var pokemonService: PokemonRepository = PokemonRepository.getInstance()
    private var pokemonList: List<Pokemon> = listOf()

    fun fetchPokemonList(
        onPokemonListReadyListener: (PokemonsAttributes) -> Unit,
        onServiceReturnError: () -> Unit
    ) {
        onListReadyListener = onPokemonListReadyListener
        pokemonService.callPokemonSource(this::onServiceFinishedWork, onServiceReturnError)
    }

    fun isPokemonListAttributesEmpty(): Boolean =
        pokemonList.isEmpty()

    fun getListPokemonAttributes(): PokemonsAttributes =
        PokemonsAttributes(map(pokemonList))

    fun getPokemonByName(namePokemon: String): Pokemon =
        pokemonList.first {
            it.name.equals(namePokemon, true)
        }

    private fun onServiceFinishedWork(pokemonAnswer: ListPokemon) {
        pokemonList = map(pokemonAnswer)
        onListReadyListener?.invoke(
            PokemonsAttributes(map(pokemonList))
        )
    }
}