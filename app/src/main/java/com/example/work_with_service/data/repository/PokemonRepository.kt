package com.example.work_with_service.data.repository

import com.example.work_with_service.data.client.PokemonApiTalker
import com.example.work_with_service.data.mappers.PokemonResourceMapper
import com.example.work_with_service.data.model.PokemonResourceList
import com.example.work_with_service.data.model.Pokemon
import com.example.work_with_service.data.model.PokemonSpecies
import com.example.work_with_service.data.model.Ability
import com.example.work_with_service.data.model.Type
import com.example.work_with_service.domain.Resource
import com.example.work_with_service.domain.Status.ERROR
import com.example.work_with_service.domain.Status.SUCCESS
import com.example.work_with_service.domain.models.PokemonDetail
import com.example.work_with_service.domain.models.Pokemon as DomainPokemon

class PokemonRepository(
    private val apiTalker: PokemonApiTalker,
    private val mapper: PokemonResourceMapper
) {
    private var onPokemonListReady: ((Resource<List<DomainPokemon>>) -> Unit)? = null
    private var onPokemonDetailReady: ((Resource<PokemonDetail>) -> Unit)? = null
    private var listPokemon: MutableList<Pokemon>? = null
    private var pokemonDetail: PokemonDetail? = null
    private var pokemonAbilities: List<String> = listOf()
    private var pokemonTypes: List<String> = listOf()

    fun fetchListPokemon(onPokemonListReady: (Resource<List<DomainPokemon>>) -> Unit) {
        this.onPokemonListReady = onPokemonListReady
        listPokemon = mutableListOf()
        apiTalker.callPokemonResourceList(OFFSET, LIMIT, this::onServiceSendPokemonResourceList)
    }

    fun fetchPokemonDetail(
        pokemonName: String,
        abilityNames: List<String>,
        typeNames: List<String>,
        onPokemonDetailReady: (Resource<PokemonDetail>) -> Unit
    ) {
        this.onPokemonDetailReady = onPokemonDetailReady
        pokemonAbilities = abilityNames
        pokemonTypes = typeNames
        fetchPokemonSpecies(pokemonName)
    }

    private fun fetchPokemonSpecies(pokemonName: String) =
        apiTalker.callPokemonSpecies(pokemonName, this::onServiceSendPokemonSpecies)

    private fun onServiceSendPokemonResourceList(resource: Resource<PokemonResourceList>) {
//        getResourcePokemonData(resource)?.let { pokemonResourceList ->
        getResource(resource, this::onPokemonListResourceStatusError)?.let { pokemonResourceList ->
            pokemonResourceList.results.forEach {
                apiTalker.callPokemon(it.name, this::onServiceSendPokemon)
            }
        }
    }

    private fun onServiceSendPokemon(resource: Resource<Pokemon>) {
//        getResourcePokemonData(resource)?.let { pokemon ->
        getResource(resource, this::onPokemonListResourceStatusError)?.let { pokemon ->
            listPokemon?.let {
                it.add(pokemon)
                if (it.size == LIMIT) {
                    onPokemonListReady?.invoke(Resource.success(mapper.map(it)))
                    listPokemon = null
                }
            }
        }
    }

    private fun onServiceSendPokemonSpecies(resource: Resource<PokemonSpecies>) {
//        getResourcePokemonDetailData(resource)?.let {
        getResource(resource, this::onPokemonDetailResourceStatusError)?.let {
            pokemonDetail = PokemonDetail(it.name, mapper.map(it))
            fetchPokemonAbilities()
        }
    }

    private fun fetchPokemonAbilities() {
        pokemonAbilities.forEach {
            apiTalker.callPokemonAbility(it, this::onServiceSendPokemonAbility)
        }
    }

    private fun onServiceSendPokemonAbility(resource: Resource<Ability>) {
//        getResourcePokemonDetailData(resource)?.let { ability ->
        getResource(resource, this::onPokemonDetailResourceStatusError)?.let { ability ->
            pokemonDetail?.abilities?.let {
                it.add(mapper.map(ability))
                if (it.size == pokemonAbilities.size) {
                    fetchPokemonTypes()
                }
            }
        }
    }

    private fun fetchPokemonTypes() =
        pokemonTypes.forEach {
            apiTalker.callPokemonType(it, this::onServiceSendPokemonType)
        }

    private fun onServiceSendPokemonType(resource: Resource<Type>) {
//        getResourcePokemonDetailData(resource)?.let { type ->
        getResource(resource, this::onPokemonDetailResourceStatusError)?.let { type ->
            pokemonDetail?.types?.let { types ->
                types.add(mapper.map(type))
                if (types.size == pokemonTypes.size) {
                    pokemonDetail?.let {
                        onPokemonDetailReady?.invoke(Resource.success(it))
                    }
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

    private fun <T> getResource(
        resource: Resource<T>,
        onStatusResourceError: (String) -> Unit
    ): T? {
        if (resource.status == ERROR) {
            onStatusResourceError(resource.message ?: UNKNOWN_ERROR)
        }
        return resource.data
    }
//
//    private fun <T> getResourcePokemonData(resource: Resource<T>): T? =
//        if (resource.status == SUCCESS) {
//            resource.data
//        } else {
//            onPokemonListReady?.invoke(Resource.error((resource.message ?: ""), null))
//            null
//        }

//
//    private fun <T> getResourcePokemonDetailData(resource: Resource<T>): T? =
//        if (resource.status == SUCCESS) {
//            resource.data
//        } else {
//            onPokemonDetailReady?.invoke(Resource.error((resource.message ?: ""), null))
//            null
//        }

    companion object {
        private const val OFFSET = 0
        private const val LIMIT = 30
        private const val UNKNOWN_ERROR = "Unknown error"
    }
}