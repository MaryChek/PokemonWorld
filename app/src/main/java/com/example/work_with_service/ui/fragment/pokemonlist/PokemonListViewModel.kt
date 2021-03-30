package com.example.work_with_service.ui.fragment.pokemonlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.work_with_service.data.entities.Pokemon
import com.example.work_with_service.data.repository.PokemonRepository
import com.example.work_with_service.ui.model.ListPokemon
import com.example.work_with_service.ui.model.ListPokemonAttributes
import com.example.work_with_service.ui.model.PokemonAttributes
import com.example.work_with_service.ui.model.ServicePokemonAnswer

class PokemonListViewModel : ViewModel() {
    val pokemonListLive = MutableLiveData<ListPokemonAttributes>()

    fun fetchPokemonList() {
        PokemonRepository.getInstance().callPokemonSource(this::onPokemonListReady)
    }

    private fun onPokemonListReady(listPokemon: ServicePokemonAnswer) {
        pokemonListLive.value = getListPokemonAttributes((listPokemon as ListPokemon).listPokemon)
    }

    private fun getListPokemonAttributes(listPokemon: List<Pokemon>): ListPokemonAttributes {
        val listAttributes: MutableList<PokemonAttributes> = mutableListOf()
        listPokemon.forEach {
            listAttributes.add(PokemonAttributes(it.sprites.frontDefault, it.name))
        }
        return ListPokemonAttributes(listAttributes)
    }
}