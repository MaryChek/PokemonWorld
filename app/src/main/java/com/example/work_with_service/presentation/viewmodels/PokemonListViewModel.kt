package com.example.work_with_service.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.work_with_service.data.repository.PokemonRepository
import com.example.work_with_service.domain.Resource
import com.example.work_with_service.domain.Status
import com.example.work_with_service.domain.models.Pokemon as DomainPokemon
import com.example.work_with_service.presentation.mappers.PokemonListMapper
import com.example.work_with_service.presentation.models.Pokemon

class PokemonListViewModel(
    private val mapper: PokemonListMapper,
    private var pokemonRepository: PokemonRepository
) : ViewModel() {
    val pokemonList = MutableLiveData<Resource<List<Pokemon>>>()

    fun fetchPokemonList() {
        pokemonList.value = Resource.loading()
        pokemonRepository.fetchListPokemon(this::onServiceFinishedWork)
    }

    private fun onServiceFinishedWork(pokemons: Resource<List<DomainPokemon>>) {
        pokemonList.value =
            if (pokemons.data != null && pokemons.status == Status.SUCCESS) {
                Resource.success(mapper.map(pokemons.data))
            } else {
                pokemons.message?.let {
                    Log.w(null, it)
                }
                Resource.error((pokemons.message ?: ""), null)
            }
    }
}