package com.example.work_with_service.ui.fragment.pokemonlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.work_with_service.data.repository.PokemonRepository
import com.example.work_with_service.data.model.ListPokemon
import com.example.work_with_service.ui.mapper.PokemonListMapper
import com.example.work_with_service.ui.model.PokemonsAttributes

class PokemonListViewModel : ViewModel() {
    private val mapper: PokemonListMapper = PokemonListMapper()
    val pokemonListLive = MutableLiveData<PokemonsAttributes>()

    fun fetchPokemonList() {
        PokemonRepository.getInstance()
            .callPokemonSource(this::onServiceFinishedWork, this::onServiceReturnError)
    }

    private fun onServiceFinishedWork(listPokemon: ListPokemon) {
        val pokemons = mapper.map(listPokemon)
        pokemonListLive.value = PokemonsAttributes(mapper.map(pokemons))
    }

    private fun onServiceReturnError() {

    }
}