package com.example.work_with_service.ui.model

import com.example.work_with_service.data.repository.PokemonRepository
import com.example.work_with_service.ui.mapper.PokemonListMapper
import com.example.work_with_service.domain.entities.Pokemon as DomainPokemon

class PokemonListModel(private val mapper: PokemonListMapper = PokemonListMapper())  {
    private var onListReadyListener: ((List<Pokemon>) -> Unit)? = null
    private var pokemonService: PokemonRepository = PokemonRepository.getInstance()
    private var pokemonList: List<Pokemon> = listOf()

    fun fetchPokemonList(
        onPokemonListReadyListener: (List<Pokemon>) -> Unit,
        onServiceReturnError: () -> Unit
    ) {
        onListReadyListener = onPokemonListReadyListener
        pokemonService.callPokemonSource(this::onServiceFinishedWork, onServiceReturnError)
    }

    fun isPokemonListEmpty(): Boolean =
        pokemonList.isEmpty()

    fun getListPokemon(): List<Pokemon> =
        pokemonList

    fun getPokemonByName(namePokemon: String): Pokemon =
        pokemonList.first {
            it.name.equals(namePokemon, true)
        }

    private fun onServiceFinishedWork(pokemonAnswer: List<DomainPokemon>) {
        pokemonList = mapper.map(pokemonAnswer)
        onListReadyListener?.invoke(pokemonList)
    }
}