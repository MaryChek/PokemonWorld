package com.example.work_with_service.presentation.viewmodels

import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import com.example.work_with_service.R
import com.example.work_with_service.domain.models.Resource
import com.example.work_with_service.domain.interactor.PokemonInteractor
import com.example.work_with_service.domain.models.Pokemon as DomainPokemon
import com.example.work_with_service.presentation.mappers.PokemonListMapper
import com.example.work_with_service.presentation.models.Pokemon
import com.example.work_with_service.presentation.models.PokemonListModel
import com.example.work_with_service.presentation.models.State
import com.example.work_with_service.presentation.navigation.Navigation

class PokemonListViewModel(
    private val mapper: PokemonListMapper,
    private val interactor: PokemonInteractor
) : BasePokemonViewModel<PokemonListModel>(mapper) {

    val navigation = MutableLiveData<Navigation>()

    fun fetchPokemonList() {
        model.value = PokemonListModel()
        interactor.fetchListPokemon(this::onPokemonGet)
    }

    private fun onPokemonGet(resource: Resource<List<DomainPokemon>>) {
        val state: State = getResourceState(resource)
        val pokemonList: List<Pokemon>? = resource.data?.let { listPokemon ->
            mapper.map(listPokemon)
        }
        model.value = PokemonListModel(state, pokemonList)
    }

    fun onItemPokemonClick(pokemon: Pokemon) {
        navigation.value = Navigation(
            R.id.action_pokemonListPageFragment_to_pokemonDetailPageFragment,
            bundleOf(KEY_FOR_POKEMON_ARG to pokemon)
        )
    }

    override fun onButtonRetryConnectionClick() =
        fetchPokemonList()

    companion object {
        private const val KEY_FOR_POKEMON_ARG = "namePokemon"
    }
}