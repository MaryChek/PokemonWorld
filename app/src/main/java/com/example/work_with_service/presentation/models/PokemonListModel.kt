package com.example.work_with_service.presentation.models

import com.example.work_with_service.data.repository.PokemonRepository
import com.example.work_with_service.presentation.mappers.PokemonListMapper

class PokemonListModel(
    private val mapper: PokemonListMapper,
    private var pokemonService: PokemonRepository
){
//    private var onListReadyListener: ((List<Pokemon>) -> Unit)? = null
//    private var pokemonList: List<Pokemon> = listOf()
//
//    fun fetchPokemonList(
//        onPokemonListReadyListener: (List<Pokemon>) -> Unit,
//        onServiceReturnError: () -> Unit
//    ) {
//        onListReadyListener = onPokemonListReadyListener
//        pokemonService.callPokemonSource(this::onServiceFinishedWork, onServiceReturnError)
//    }
//
//    fun isPokemonListEmpty(): Boolean =
//        pokemonList.isEmpty()
//
//    fun getListPokemon(): List<Pokemon> =
//        pokemonList
//
//    fun getPokemonByName(namePokemon: String): Pokemon =
//        pokemonList.first {
//            it.name.equals(namePokemon, true)
//        }
//
//    private fun onServiceFinishedWork(pokemonAnswer: List<DomainPokemon>) {
//        pokemonList = mapper.map(pokemonAnswer)
//        onListReadyListener?.invoke(pokemonList)
//    }
}