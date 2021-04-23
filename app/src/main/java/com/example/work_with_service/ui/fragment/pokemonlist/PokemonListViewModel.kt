package com.example.work_with_service.ui.fragment.pokemonlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.work_with_service.data.repository.PokemonRepository
import com.example.work_with_service.domain.entities.Pokemon as DomainPokemon
import com.example.work_with_service.ui.mapper.PokemonListMapper
import com.example.work_with_service.ui.model.Pokemon

class PokemonListViewModel : ViewModel() {
    private val mapper: PokemonListMapper = PokemonListMapper()
    val pokemonListLive = MutableLiveData<List<Pokemon>>()

    fun fetchPokemonList() {
        PokemonRepository.getInstance()
            .callPokemonSource(this::onServiceFinishedWork, this::onServiceReturnError)
    }

    private fun onServiceFinishedWork(pokemons: List<DomainPokemon>) {
        pokemonListLive.value = mapper.map(pokemons)
    }

    private fun onServiceReturnError() {

    }
}