package com.example.work_with_service.domain.interactor

import com.example.work_with_service.data.repository.PokemonRepository
import com.example.work_with_service.domain.models.Resource
import com.example.work_with_service.domain.models.Status
import com.example.work_with_service.domain.models.*

class PokemonInteractor(
    private val repository: PokemonRepository,
) {
    private var onPokemonListReady: ((Resource<List<Pokemon>>) -> Unit)? = null
    private var onPokemonDetailReady: ((Resource<PokemonDetail>) -> Unit)? = null

    private var abilityNames: List<String>? = null
    private var typeNames: List<String>? = null

    private var pokemonList: MutableList<Pokemon> = mutableListOf()
    private var pokemonDetail: PokemonDetail? = null

    fun fetchListPokemon(onPokemonListReady: (Resource<List<Pokemon>>) -> Unit) {
        this.onPokemonListReady = onPokemonListReady
        repository.fetchListPokemonResource(this::onPokemonResourceListReady, OFFSET, LIMIT)
    }

    private fun onPokemonResourceListReady(pokemonNames: Resource<List<String>>) {
        getResourceData(pokemonNames, this::onPokemonListResourceStatusError)?.let { names ->
            names.forEach { name ->
                repository.fetchPokemon(name, this::onPokemonReady)
            }
        }
    }

    private fun onPokemonReady(pokemonResource: Resource<Pokemon>) {
        getResourceData(pokemonResource, this::onPokemonListResourceStatusError)?.let { pokemon ->
            pokemonList.add(pokemon)
            if (pokemonList.size == LIMIT) {
                onPokemonListReady?.invoke(Resource.success(pokemonList))
                pokemonList = mutableListOf()
            }
        }
    }

    fun fetchPokemonDetail(
        namePokemon: String,
        abilityNames: List<String>,
        typeNames: List<String>,
        onPokemonDetailReady: (Resource<PokemonDetail>) -> Unit
    ) {
        this.abilityNames = abilityNames
        this.typeNames = typeNames
        this.onPokemonDetailReady = onPokemonDetailReady
        repository.fetchPokemonSpecies(namePokemon, this::onPokemonSpeciesReady)
    }

    private fun onPokemonSpeciesReady(speciesResource: Resource<PokemonSpecies>) {
        getResourceData(speciesResource, this::onPokemonDetailResourceStatusError)?.let { species ->
            pokemonDetail = PokemonDetail(species)
            fetchPokemonAbilities()
        }
    }

    private fun fetchPokemonAbilities() {
        abilityNames?.forEach { name ->
            repository.fetchPokemonAbility(name, this::onAbilityReady)
        }
    }

    private fun onAbilityReady(abilityResource: Resource<Ability>) {
        getResourceData(abilityResource, this::onPokemonDetailResourceStatusError)?.let { ability ->
            pokemonDetail?.apply {
                abilities.add(ability)
                if (abilities.size == abilityNames?.size) {
                    fetchPokemonTypes()
                }
            }
        }
    }

    private fun fetchPokemonTypes() {
        typeNames?.forEach { type ->
            repository.fetchPokemonType(type, this::onPokemonTypeReady)
        }
    }

    private fun onPokemonTypeReady(typeResource: Resource<Type>) {
        getResourceData(typeResource, this::onPokemonDetailResourceStatusError)?.let { type ->
            pokemonDetail?.apply {
                types.add(type)
                if (types.size == typeNames?.size) {
                    onPokemonDetailReady?.invoke(Resource.success(this))
                }
            }
        }
    }

    private fun onPokemonListResourceStatusError(message: String) {
        onPokemonListReady?.invoke(Resource.error((message), null))
    }

    private fun onPokemonDetailResourceStatusError(message: String) {
        onPokemonDetailReady?.invoke(Resource.error((message), null))
    }

    private fun <T> getResourceData(
        resource: Resource<T>,
        onStatusResourceError: (String) -> Unit
    ): T? {
        if (resource.status == Status.ERROR) {
            onStatusResourceError(resource.message ?: UNKNOWN_ERROR)
        }
        return resource.data
    }

    companion object {
        private const val OFFSET = 0
        private const val LIMIT = 30
        private const val UNKNOWN_ERROR = "Unknown error"
    }
}