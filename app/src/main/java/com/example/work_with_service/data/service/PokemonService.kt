package com.example.work_with_service.data.service

import android.util.Log
import com.example.work_with_service.data.client.PokeApiClient
import com.example.work_with_service.data.service.Resource.Status.*
import com.example.work_with_service.data.entities.*
import com.example.work_with_service.ui.model.ListPokemon
import com.example.work_with_service.ui.model.PokiInfo
import com.example.work_with_service.ui.model.ServicePokemonAnswer

class PokemonService(
    private val onServiceFinishedWork: (ServicePokemonAnswer) -> Unit
) {
    private val remotePokemonSource = PokeApiClient(this::onServiceCallAnswer)
    private var pokemon: Pokemon? = null
    private var listPokemon: MutableList<Pokemon>? = null
    private var pokemonInfo: PokiInfo? = null

    fun callPokemonSource() {
        listPokemon = mutableListOf()
        remotePokemonSource.callPokemonResourceList(OFFSET, LIMIT)
    }

    fun callPokemonInfo(pokemon: Pokemon) {
        this.pokemon = pokemon
        pokemonInfo = PokiInfo()
        remotePokemonSource.callPokemonSpecies(pokemon.name)
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
                        onServiceFinishedWork(ListPokemon(it))
                        listPokemon = null
                    }
                }
            }
            is PokemonSpecies -> {
                pokemonInfo?.pokemonSpecies = pokemonResource
                pokemon?.abilities?.forEach {
                    remotePokemonSource.callPokemonAbility(it.ability.name)
                }
            }
            is Ability -> {
                pokemonInfo?.abilities?.apply {
                    add(pokemonResource)
                    if (size == pokemon?.abilities?.size) {
                        pokemon?.types?.forEach {
                            remotePokemonSource.callPokemonType(it.type.name)
                        }
                    }
                }
            }
            is Type -> {
                pokemonInfo?.types?.apply {
                    add(pokemonResource)
                    if (size == pokemon?.types?.size) {
                        pokemonInfo?.let {
                            onServiceFinishedWork(it)
                            pokemonInfo = null
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
        pokemonInfo = null
        pokemon = null
    }

    companion object {
        private const val OFFSET = 0
        private const val LIMIT = 30
    }
}