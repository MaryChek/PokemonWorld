package com.example.work_with_service.data.repository

import com.example.work_with_service.data.api.PokemonApiTalker
import com.example.work_with_service.data.mappers.PokemonResourceMapper
import com.example.work_with_service.data.api.model.PokemonResourceList
import com.example.work_with_service.data.api.model.Pokemon
import com.example.work_with_service.data.api.model.PokemonSpecies
import com.example.work_with_service.data.api.model.Ability
import com.example.work_with_service.data.api.model.Type
import com.example.work_with_service.domain.models.Resource
import com.example.work_with_service.domain.models.PokemonDetail
import com.example.work_with_service.domain.models.Pokemon as DomainPokemon
import com.example.work_with_service.domain.models.PokemonSpecies as DomainSpecies
import com.example.work_with_service.domain.models.Ability as DomainAbility
import com.example.work_with_service.domain.models.Type as DomainType


class PokemonRepository(
    private val apiTalker: PokemonApiTalker,
    private val mapper: PokemonResourceMapper
) {
    private var onPokemonResourceListReady: ((Resource<List<String>>) -> Unit)? = null
    private var onPokemonReady: ((Resource<DomainPokemon>) -> Unit)? = null
    private var onPokemonSpeciesReady: ((Resource<DomainSpecies>) -> Unit)? = null
    private var onAbilityReady: ((Resource<DomainAbility>) -> Unit)? = null
    private var onTypeReady: ((Resource<DomainType>) -> Unit)? = null


    private var onPokemonDetailReady: ((Resource<PokemonDetail>) -> Unit)? = null

    //    private var listPokemon: MutableList<Pokemon>? = mutableListOf()
//    private var pokemonDetail: PokemonDetail? = null
//    private var pokemonAbilities: List<String> = listOf()
//    private var pokemonTypes: List<String> = listOf()

    fun fetchListPokemonResource(
        onPokemonResourceListReady: (Resource<List<String>>) -> Unit,
        offset: Int,
        limit: Int
    ) {
//        listPokemon = mutableListOf()
        this.onPokemonResourceListReady = onPokemonResourceListReady
        apiTalker.callPokemonResourceList(offset, limit, this::onServiceSendPokemonResourceList)
    }

    fun fetchPokemon(name: String, onPokemonReady: (Resource<DomainPokemon>) -> Unit) {
        this.onPokemonReady = onPokemonReady
        apiTalker.callPokemon(name, this::onServiceSendPokemon)
    }

    private fun onServiceSendPokemonResourceList(resource: Resource<PokemonResourceList>) {
        onPokemonResourceListReady?.invoke(
            getResourceAfterMap(resource, mapper::mapPokemonResourceList)
//            Resource(resource.status, mapper.map(resource.data), resource.message)
        )
//        getResourcePokemonData(resource)?.let { pokemonResourceList ->
//        getResource(resource, this::onPokemonListResourceStatusError)?.let { pokemonResourceList ->
//            pokemonResourceList.results.forEach {
//                apiTalker.callPokemon(it.name, this::onServiceSendPokemon)
//            }
//        }
    }

    private fun onServiceSendPokemon(resource: Resource<Pokemon>) {
        onPokemonReady?.invoke(
            getResourceAfterMap(resource, mapper::mapPokemon)
//            Resource(resource.status, mapper.map(resource.data), resource.message)
        )
//        getResourcePokemonData(resource)?.let { pokemon ->
//        getResource(resource, this::onPokemonListResourceStatusError)?.let { pokemon ->
//            listPokemon?.let {
//                it.add(pokemon)
//                if (it.size == LIMIT) {
//                    onPokemonResourceListReady?.invoke(Resource.success(mapper.map(it)))
//                    listPokemon = null
//                }
//            }
//        }
    }

//    fun fetchPokemonDetail(
//        pokemonName: String,
//        abilityNames: List<String>,
//        typeNames: List<String>,
//        onPokemonDetailReady: (Resource<PokemonDetail>) -> Unit
//    ) {
//        this.onPokemonDetailReady = onPokemonDetailReady
//        pokemonAbilities = abilityNames
//        pokemonTypes = typeNames
////        fetchPokemonSpecies(pokemonName)
//    }

    fun fetchPokemonSpecies(
        pokemonName: String,
        onPokemonSpeciesReady: (Resource<DomainSpecies>) -> Unit
    ) {
        this.onPokemonSpeciesReady = onPokemonSpeciesReady
        apiTalker.callPokemonSpecies(pokemonName, this::onServiceSendPokemonSpecies)
    }

    private fun onServiceSendPokemonSpecies(resource: Resource<PokemonSpecies>) {
        onPokemonSpeciesReady?.invoke(
            getResourceAfterMap(resource, mapper::mapSpecies)
        )
//        getResourcePokemonDetailData(resource)?.let {
//        getResource(resource, this::onPokemonDetailResourceStatusError)?.let {
//            pokemonDetail = PokemonDetail(it.name, mapper.mapPokemonResourceList(it))
//            fetchPokemonAbilities()
//        }
    }

    fun fetchPokemonAbility(
        nameAbility: String,
        onAbilityReady: (Resource<DomainAbility>) -> Unit
    ) {
        this.onAbilityReady = onAbilityReady
        apiTalker.callPokemonAbility(nameAbility, this::onServiceSendPokemonAbility)
    }

    private fun onServiceSendPokemonAbility(resource: Resource<Ability>) {
        onAbilityReady?.invoke(
            getResourceAfterMap(resource, mapper::mapAbility)
        )
//        getResourcePokemonDetailData(resource)?.let { ability ->
//        getResource(resource, this::onPokemonDetailResourceStatusError)?.let { ability ->
//            pokemonDetail?.abilities?.let {
//                it.add(mapper.mapAbility(ability))
//                if (it.size == pokemonAbilities.size) {
//                    fetchPokemonTypes()
//                }
//            }
//        }
    }

    fun fetchPokemonType(nameType: String, onTypeReady: (Resource<DomainType>) -> Unit) {
        this.onTypeReady = onTypeReady
        apiTalker.callPokemonType(nameType, this::onServiceSendPokemonType)
    }

    private fun onServiceSendPokemonType(resource: Resource<Type>) {
        onTypeReady?.invoke(
            getResourceAfterMap(resource, mapper::mapType)
        )
//        getResourcePokemonDetailData(resource)?.let { type ->
//        getResource(resource, this::onPokemonDetailResourceStatusError)?.let { type ->
//            pokemonDetail?.types?.let { types ->
//                types.add(mapper.mapPokemonResourceList(type))
//                if (types.size == pokemonTypes.size) {
//                    pokemonDetail?.let {
//                        onPokemonDetailReady?.invoke(Resource.success(it))
//                    }
//                }
//            }
//        }
    }

    private fun onPokemonListResourceStatusError(message: String) {
        onPokemonResourceListReady?.invoke(Resource.error((message), null))
    }

    private fun onPokemonDetailResourceStatusError(message: String) {
        onPokemonDetailReady?.invoke(Resource.error((message), null))
    }

    private fun <T, R> getResourceAfterMap(
        resource: Resource<T>,
        map: (T?) -> R?
    ): Resource<R> =
        Resource(resource.status, map(resource.data), resource.message)

//        if (resource.status == ERROR) {
//            onStatusResourceError(resource.message ?: UNKNOWN_ERROR)
//        }
//        return resource.data
//    }
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
        //        private const val OFFSET = 0
//        private const val LIMIT = 30
        private const val UNKNOWN_ERROR = "Unknown error"
    }
}