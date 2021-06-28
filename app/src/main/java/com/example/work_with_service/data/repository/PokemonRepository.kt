package com.example.work_with_service.data.repository

import com.example.work_with_service.data.api.PokemonApiTalker
import com.example.work_with_service.data.mappers.PokemonResourceMapper
import com.example.work_with_service.data.api.model.PokemonResourceList
import com.example.work_with_service.data.api.model.Pokemon
import com.example.work_with_service.data.api.model.PokemonSpecies
import com.example.work_with_service.data.api.model.Ability
import com.example.work_with_service.data.api.model.Type
import com.example.work_with_service.domain.models.Resource
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

    fun fetchListPokemonResource(
        onPokemonResourceListReady: (Resource<List<String>>) -> Unit,
        offset: Int,
        limit: Int
    ) {
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
        )
    }

    private fun onServiceSendPokemon(resource: Resource<Pokemon>) {
        onPokemonReady?.invoke(
            getResourceAfterMap(resource, mapper::mapPokemon)
        )
    }

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
    }

    fun fetchPokemonType(nameType: String, onTypeReady: (Resource<DomainType>) -> Unit) {
        this.onTypeReady = onTypeReady
        apiTalker.callPokemonType(nameType, this::onServiceSendPokemonType)
    }

    private fun onServiceSendPokemonType(resource: Resource<Type>) {
        onTypeReady?.invoke(
            getResourceAfterMap(resource, mapper::mapType)
        )
    }

    private fun <T, R> getResourceAfterMap(
        resource: Resource<T>,
        map: (T?) -> R?
    ): Resource<R> =
        Resource(resource.status, map(resource.data), resource.message)
}