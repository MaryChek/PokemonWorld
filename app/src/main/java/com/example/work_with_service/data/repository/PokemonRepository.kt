package com.example.work_with_service.data.repository

import android.util.Log
import com.example.work_with_service.data.client.PokeApiClient
import com.example.work_with_service.data.mapper.PokemonResourceMapper
import com.example.work_with_service.data.model.PokemonResourceList
import com.example.work_with_service.data.model.Pokemon
import com.example.work_with_service.data.model.PokemonSpecies
import com.example.work_with_service.data.model.Ability
import com.example.work_with_service.data.model.Type
import com.example.work_with_service.data.service.Resource
import com.example.work_with_service.data.service.Status.SUCCESS
import com.example.work_with_service.domain.entities.PokemonDetail
import com.example.work_with_service.domain.entities.Pokemon as DomainPokemon

class PokemonRepository(
    private val remotePokemonSource: PokeApiClient,
    private val mapper: PokemonResourceMapper
) {
    private var onPokemonListReady: ((List<DomainPokemon>) -> Unit)? = null
    private var onPokemonDetailReady: ((PokemonDetail) -> Unit)? = null
    private var onServiceFinishedError: (() -> Unit)? = null
    private var listPokemon: MutableList<Pokemon>? = null
    private var pokemonDetail: PokemonDetail? = null
    private var pokemonAbilities: List<String> = listOf()
    private var pokemonTypes: List<String> = listOf()

    fun callPokemonSource(
        onPokemonListReady: (List<DomainPokemon>) -> Unit,
        onServiceFinishedError: () -> Unit
    ) {
        this.onPokemonListReady = onPokemonListReady
        this.onServiceFinishedError = onServiceFinishedError
        listPokemon = mutableListOf()
        remotePokemonSource.callPokemonResourceList(
            OFFSET,
            LIMIT,
            this::onServiceSendPokemonResourceList
        )
    }

    fun callPokemonDetail(
        pokemonName: String,
        abilityNames: List<String>,
        typeNames: List<String>,
        onPokemonDetailReady: (PokemonDetail) -> Unit,
        onServiceFinishedError: () -> Unit
    ) {
        this.onPokemonDetailReady = onPokemonDetailReady
        this.onServiceFinishedError = onServiceFinishedError
        pokemonAbilities = abilityNames
        pokemonTypes = typeNames
        callPokemonSpecies(pokemonName)
    }

    private fun callPokemonSpecies(pokemonName: String) =
        remotePokemonSource.callPokemonSpecies(pokemonName, this::onServiceSendPokemonSpecies)

    private fun callPokemonAbilities() {
        pokemonAbilities.forEach {
            remotePokemonSource.callPokemonAbility(it, this::onServiceSendPokemonAbility)
        }
    }

    private fun callPokemonTypes() {
        pokemonTypes.forEach {
            remotePokemonSource.callPokemonType(it, this::onServiceSendPokemonType)
        }
    }

    private fun onServiceSendPokemonResourceList(resource: Resource<PokemonResourceList>) {
        getResourceData(resource)?.let { pokemonResourceList ->
            pokemonResourceList.results.forEach {
                remotePokemonSource.callPokemon(it.name, this::onServiceSendPokemon)
            }
        }
    }

    private fun onServiceSendPokemon(resource: Resource<Pokemon>) {
        getResourceData(resource)?.let { pokemon ->
            addPokemonToList(pokemon)
            listPokemon?.let {
                if (it.size == LIMIT) {
                    onPokemonListReady?.invoke(mapper.map(it))
                    listPokemon = null
                }
            }
        }
    }

    private fun onServiceSendPokemonSpecies(resource: Resource<PokemonSpecies>) {
        getResourceData(resource)?.let {
            pokemonDetail = PokemonDetail(it.name, mapper.map(it))
            callPokemonAbilities()
        }
    }

    private fun onServiceSendPokemonAbility(resource: Resource<Ability>) {
        getResourceData(resource)?.let { ability ->
            pokemonDetail?.abilities?.let {
                it.add(mapper.map(ability))
                if (it.size == pokemonAbilities.size) {
                    callPokemonTypes()
                }
            }
        }
    }

    private fun onServiceSendPokemonType(resource: Resource<Type>) {
        getResourceData(resource)?.let { type ->
            pokemonDetail?.types?.let { types ->
                types.add(mapper.map(type))
                if (types.size == pokemonTypes.size) {
                    pokemonDetail?.let {
                        onPokemonDetailReady?.invoke(it)
                    }
                }
            }
        }
    }

    private fun <T> getResourceData(res: Resource<T>): T? =
        if (res.status == SUCCESS) {
            res.data
        } else {
            onResponseError(res.message)
            null
        }

    private fun addPokemonToList(pokemon: Pokemon) {
        listPokemon?.add(pokemon)
    }

    private fun onResponseError(message: String?) {
        message?.let {
            Log.e(null, message)
        }
        onServiceFinishedError?.invoke()
    }

    companion object {
        private const val OFFSET = 0
        private const val LIMIT = 30

//        private var INSTANCE: PokemonRepository? = null

//        fun getInstance(
//            remotePokemonSource: PokeApiClient,
//            mapper: PokemonResourceMapper
//        ): PokemonRepository =
//            INSTANCE ?: PokemonRepository(remotePokemonSource, mapper).also {
//                INSTANCE = it
//            }
    }
}