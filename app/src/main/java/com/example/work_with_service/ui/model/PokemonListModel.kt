package com.example.work_with_service.ui.model

import com.example.work_with_service.data.entities.Pokemon
import com.example.work_with_service.data.service.PokemonService

class PokemonListModel {
    private var onListReadyListener: ((PokiAttributes) -> Unit)? = null
    private var pokemonService: PokemonService = PokemonService()
    private var pokemonList: List<Pokemon> = listOf()

    init {
        pokemonService
    }

    fun fetchPokemonList(onPokemonListReadyListener: (PokiAttributes) -> Unit) {
        onListReadyListener = onPokemonListReadyListener
//        pokemonService.callPokemonSource(this::onServiceFinishedWork)
    }

    fun isPokemonListAttributesEmpty(): Boolean =
        pokemonList.isEmpty()

    fun getListPokemonAttributes(): ListPokemonAttributes =
        getListPokemonAttributes(pokemonList)

    private fun onServiceFinishedWork(pokemonAnswer: ListPokemon) {
        pokemonList = pokemonAnswer.listPokemon
        onListReadyListener?.invoke(getListPokemonAttributes(pokemonList))
    }

    private fun getListPokemonAttributes(listPokemon: List<Pokemon>): ListPokemonAttributes {
        val listAttributes: MutableList<PokemonAttributes> = mutableListOf()
        listPokemon.forEach {
            listAttributes.add(PokemonAttributes(it.sprites.frontDefault, it.name))
        }
        return ListPokemonAttributes(listAttributes)
    }
}
