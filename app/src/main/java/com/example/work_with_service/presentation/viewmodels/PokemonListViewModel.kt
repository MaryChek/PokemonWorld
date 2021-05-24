package com.example.work_with_service.presentation.viewmodels

import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.work_with_service.R
import com.example.work_with_service.data.repository.PokemonRepository
import com.example.work_with_service.domain.Resource
import com.example.work_with_service.presentation.models.Receipt.Status
import com.example.work_with_service.domain.Status as DomainStatus
import com.example.work_with_service.domain.models.Pokemon as DomainPokemon
import com.example.work_with_service.presentation.mappers.PokemonListMapper
import com.example.work_with_service.presentation.models.Pokemon
import com.example.work_with_service.presentation.models.Receipt
import com.example.work_with_service.presentation.navigation.Navigation

class PokemonListViewModel(
    private val mapper: PokemonListMapper,
    private var pokemonRepository: PokemonRepository
) : ViewModel() {
    val pokemonList = MutableLiveData<List<Pokemon>>()
    val navigation = MutableLiveData<Navigation>()
    var receipt = MutableLiveData<Receipt>()

    fun fetchPokemonList() {
        receipt.value = Receipt.loading()
        pokemonRepository.fetchListPokemon(this::onServiceFinishedWork)
    }

    private fun onServiceFinishedWork(pokemons: Resource<List<DomainPokemon>>) {
        if (pokemons.data != null && pokemons.status == DomainStatus.SUCCESS) {
            receipt.value = Receipt.success()
            pokemonList.value = mapper.map(pokemons.data)
        } else {
            pokemons.message?.let {
                Log.w(null, it)
            }
            receipt.value = Receipt.error()
        }
    }

    fun onItemPokemonClick(pokemon: Pokemon) {
        navigation.value = Navigation(
            R.id.action_pokemonListPageFragment_to_pokemonDetailPageFragment,
            bundleOf(KEY_FOR_POKEMON_ARG to pokemon)
        )
    }

    fun onButtonRetryConnectionClick() {
        fetchPokemonList()
    }

    companion object {
        private const val KEY_FOR_POKEMON_ARG = "namePokemon"
    }
}