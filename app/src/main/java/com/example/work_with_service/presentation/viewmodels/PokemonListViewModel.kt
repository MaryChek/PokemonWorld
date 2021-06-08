package com.example.work_with_service.presentation.viewmodels

import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.work_with_service.R
import com.example.work_with_service.domain.models.Resource
import com.example.work_with_service.domain.interactor.PokemonInteractor
import com.example.work_with_service.domain.models.Pokemon as DomainPokemon
import com.example.work_with_service.presentation.mappers.PokemonListMapper
import com.example.work_with_service.presentation.models.Pokemon
import com.example.work_with_service.presentation.models.PokemonListModel
import com.example.work_with_service.presentation.models.Status
import com.example.work_with_service.presentation.navigation.Navigation

class PokemonListViewModel(
    private val mapper: PokemonListMapper,
    private val interactor: PokemonInteractor
) : ViewModel() {
    val pokemonList = MutableLiveData<List<Pokemon>>()
    val navigation = MutableLiveData<Navigation>()
    val model = MutableLiveData<PokemonListModel>()

    fun fetchPokemonList() {
        model.value = PokemonListModel(Status.loading())
        interactor.fetchListPokemon(this::onServiceFinishedWork)
    }

    private fun onServiceFinishedWork(pokemons: Resource<List<DomainPokemon>>) {
        val status: Status = mapper.mapStatus(pokemons.status, pokemons.message)
        when {
            status.isInErrorState() && status.errorMassage != null ->
                Log.e(null, status.errorMassage)
            pokemons.data != null ->
                pokemonList.value = mapper.map(pokemons.data)
            else ->
                Log.e(null, UNKNOWN_ERROR)
        }
        model.value = PokemonListModel(status)
    }

    fun onItemPokemonClick(pokemon: Pokemon) {
        navigation.value = Navigation(
            R.id.action_pokemonListPageFragment_to_pokemonDetailPageFragment,
            bundleOf(KEY_FOR_POKEMON_ARG to pokemon)
        )
    }

    fun onButtonRetryConnectionClick() =
        fetchPokemonList()

    companion object {
        private const val UNKNOWN_ERROR = "Unknown error"
        private const val KEY_FOR_POKEMON_ARG = "namePokemon"
    }
}