package com.example.work_with_service.presentation.viewmodels

import androidx.core.os.bundleOf
import com.example.work_with_service.R
import com.example.work_with_service.domain.models.Resource
import com.example.work_with_service.domain.interactor.PokemonInteractor
import com.example.work_with_service.domain.models.Pokemon as DomainPokemon
import com.example.work_with_service.presentation.mappers.PokemonListMapper
import com.example.work_with_service.presentation.models.Pokemon
import com.example.work_with_service.presentation.models.PokemonListModel
import com.example.work_with_service.presentation.models.State
import com.example.work_with_service.presentation.navigation.FromPokemonList
import com.example.work_with_service.presentation.viewmodels.base.BasePokemonViewModel

class PokemonListViewModel(
    private val mapper: PokemonListMapper,
    private val interactor: PokemonInteractor
) : BasePokemonViewModel<PokemonListModel, FromPokemonList>(PokemonListModel(), mapper) {

    private fun updateModel(
        pokemons: List<Pokemon>? = model.pokemons,
        state: State = model.state,
    ) {
        model = PokemonListModel(state = state, pokemons = pokemons)
        updateScreen()
    }

    fun fetchPokemonList() =
        interactor.fetchListPokemon(this::onPokemonGet)

    private fun onPokemonGet(resource: Resource<List<DomainPokemon>>) {
        val state: State = getResourceState(resource)
        val pokemonList: List<Pokemon>? = resource.data?.let { listPokemon ->
            mapper.map(listPokemon)
        }
        updateModel(pokemonList, state)
    }

    fun onItemPokemonClick(pokemon: Pokemon) {
        goToScreen(FromPokemonList.PokemonDetail(
            R.id.PokemonDetailFragment,
            bundleOf(Pokemon::class.java.simpleName to pokemon)
        ))
    }

    override fun goToPrevious() {
        interactor.cleanPokemonCache()
        goToScreen(FromPokemonList.PreviousScreen)
    }

    override fun onButtonRetryConnectionClick() =
        fetchPokemonList()
}