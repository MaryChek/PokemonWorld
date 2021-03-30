package com.example.work_with_service.data.repository

import android.util.Log
import com.example.work_with_service.data.client.PokeApiClient
import com.example.work_with_service.data.service.Resource.Status.*
import com.example.work_with_service.data.entities.*
import com.example.work_with_service.data.service.Resource
import com.example.work_with_service.ui.model.ListPokemon
import com.example.work_with_service.ui.model.PokiDetail
import com.example.work_with_service.ui.model.ServicePokemonAnswer

class PokemonRepository {
    private var onServiceFinishedWork: ((ServicePokemonAnswer) -> Unit)? = null
    private val remotePokemonSource = PokeApiClient(this::onServiceCallAnswer)
    private var pokemon: Pokemon? = null
    private var listPokemon: MutableList<Pokemon>? = null
    private var pokemonDetail: PokiDetail? = null

    fun callPokemonSource(onPokemonListReady: (ServicePokemonAnswer) -> Unit) {
        onServiceFinishedWork = onPokemonListReady
        listPokemon = mutableListOf()
        remotePokemonSource.callPokemonResourceList(OFFSET, LIMIT)
    }

    fun callPokemonInfo(pokemonName: String, onPokemonDetailReady: (ServicePokemonAnswer) -> Unit) {
        onServiceFinishedWork = onPokemonDetailReady
        pokemon = findPokemonByName(pokemonName)
        pokemon?.let {
            pokemonDetail = PokiDetail(
                it.name, it.baseExperience, it.height, it.weight
            )
            remotePokemonSource.callPokemonSpecies(it.name)
        }
    }

    private fun findPokemonByName(name: String): Pokemon? =
        listPokemon?.first {
            it.name == name
        }

    private fun onServiceCallAnswer(res: Resource<PokemonResource>) =
        when (res.status) {
            SUCCESS -> onResponseSuccess(res.data)
            ERROR -> onResponseError(res.message)
        }

    private fun onResponseSuccess(pokemonResource: PokemonResource?) {
        when (pokemonResource) {
            is PokemonResourceList -> {
                pokemonResource.results.forEach {
                    remotePokemonSource.callPokemon(it.name)
                }
            }
            is Pokemon -> {
                addPokemonToList(pokemonResource)
                if (listPokemon?.size == LIMIT) {
                    listPokemon?.let {
                        onServiceFinishedWork?.invoke(ListPokemon(it))
                        listPokemon = null
                    }
                }
            }
            is PokemonSpecies -> {
                pokemonDetail?.pokemonSpecies = pokemonResource
                pokemon?.abilities?.forEach {
                    remotePokemonSource.callPokemonAbility(it.ability.name)
                }
            }
            is Ability -> {
                pokemonDetail?.abilities?.apply {
                    add(pokemonResource)
                    if (size == pokemon?.abilities?.size) {
                        pokemon?.types?.forEach {
                            remotePokemonSource.callPokemonType(it.type.name)
                        }
                    }
                }
            }
            is Type -> {
                pokemonDetail?.types?.apply {
                    add(pokemonResource)
                    if (size == pokemon?.types?.size) {
                        pokemonDetail?.let {
                            onServiceFinishedWork?.invoke(it)
                            pokemonDetail = null
                        }
                        pokemon = null
                    }
                }

            }
        }
    }

    private fun addPokemonToList(pokemon: Pokemon) {
        listPokemon?.add(pokemon)
    }

    private fun onResponseError(message: String?) {
        message?.let {
            Log.e(null, it)
        }
        pokemonDetail = null
        pokemon = null
    }

    companion object {
        private const val OFFSET = 0
        private const val LIMIT = 30

        private var INSTANCE: PokemonRepository? = null

        fun getInstance(): PokemonRepository =
            INSTANCE ?: PokemonRepository().also {
                INSTANCE = it
            }
    }
}