package com.example.work_with_service.model

import com.example.work_with_service.client.PokeApiClient
import com.example.work_with_service.model.Resource.Status.*

class PokemonService(
    private val onServiceFinishedWork: (ServicePokemonAnswer) -> Unit
) {
    private val remotePokemonSource: PokeApiClient = PokeApiClient(this::onServiceCallAnswer)
    private var pokemonApiResourceList: PokemonApiResourceList? = null
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
            SUCCESS -> onResponseSuccess(res.data!!)
            ERROR -> onResponseError(res.message)
        }

    private fun onResponseSuccess(pokemonResource: PokemonResource) {
        when (pokemonResource) {
            is PokemonApiResourceList -> {
                pokemonApiResourceList = pokemonResource
                pokemonResource.results.forEach {
                    remotePokemonSource.callPokemon(it.name)
                }
            }
            is Pokemon -> {
                addPokemonToList(pokemonResource)
                if (listPokemon?.size == LIMIT) {
                    onServiceFinishedWork(ListPokemon(listPokemon!!))
                    listPokemon = null
                }
            }
            is PokemonSpecies -> {
                pokemonInfo?.pokemonSpecies = pokemonResource
                pokemon?.abilities?.forEach {
                    remotePokemonSource.callPokemonAbility(it.ability.name)
                }
            }
            is Ability -> {
                pokemonInfo?.ability!!.add(pokemonResource)
                if (pokemonInfo?.ability!!.size == pokemon?.abilities?.size) {
                    pokemon?.types?.forEach {
                        remotePokemonSource.callPokemonType(it.type.name)
                    }
                }
            }
            is Type -> {
                pokemonInfo?.type!!.add(pokemonResource)
                if (pokemonInfo?.type!!.size == pokemon?.types?.size) {
                    onServiceFinishedWork(pokemonInfo!!)
                    pokemonInfo = null
                    pokemon = null
                }
            }
        }
    }

    private fun addPokemonToList(pokemon: Pokemon) {
        listPokemon?.add(pokemon)
    }

    private fun onResponseError(message: String?) =
        println(message)

    companion object {
        private const val OFFSET = 0
        private const val LIMIT = 30
    }
}