package com.example.work_with_service.data.service

import android.util.Log
import com.example.work_with_service.data.client.PokeApiClient
import com.example.work_with_service.data.model.PokemonResource
import com.example.work_with_service.data.model.Pokemon
import com.example.work_with_service.data.model.Ability
import com.example.work_with_service.data.model.PokemonResourceList
import com.example.work_with_service.data.model.PokemonSpecies
import com.example.work_with_service.data.model.Type
import com.example.work_with_service.ui.model.servicepokemonanswer.ListPokemon
import com.example.work_with_service.ui.model.servicepokemonanswer.PokiDetail
import com.example.work_with_service.data.service.Status.*

class PokemonService {
    private var onPokemonListReady: ((ListPokemon) -> Unit)? = null
    private var onPokemonDetailReady: ((PokiDetail) -> Unit)? = null
    private var onServiceFinishedError: (() -> Unit)? = null
    private val remotePokemonSource = PokeApiClient(this::onServiceCallAnswer)
    private var pokemon: Pokemon? = null
    private var listPokemon: MutableList<Pokemon>? = null
    private var pokemonDetail: PokiDetail? = null

    fun callPokemonSource(
        onPokemonListReady: (ListPokemon) -> Unit,
        onServiceFinishedError: () -> Unit
    ) {
        this.onPokemonListReady = onPokemonListReady
        this.onServiceFinishedError = onServiceFinishedError
        listPokemon = mutableListOf()
        remotePokemonSource.callPokemonResourceList(OFFSET, LIMIT)
    }

    fun callPokemonDetail(
        pokemon: Pokemon,
        onPokemonDetailReady: (PokiDetail) -> Unit,
        onServiceFinishedError: () -> Unit
    ) {
        this.onPokemonDetailReady = onPokemonDetailReady
        this.onServiceFinishedError = onServiceFinishedError
        this.pokemon = pokemon
        pokemonDetail = PokiDetail(
            pokemon.frontDefault,
            pokemon.name,
            pokemon.baseExperience,
            pokemon.height,
            pokemon.weight
        )
        remotePokemonSource.callPokemonSpecies(pokemon.name)
    }

    private fun onServiceCallAnswer(res: Resource<PokemonResource?>) {
        when (res.status == SUCCESS) {
            true -> onResponseSuccess(res.data)
            false -> onResponseError(res.message)
        }
    }


    private fun onResponseSuccess(pokemonResource: PokemonResource?) {
        when (pokemonResource) {
            is PokemonResourceList -> {
                pokemonResource.pokemonNames.forEach {
                    remotePokemonSource.callPokemon(it)
                }
            }
            is Pokemon -> {
                addPokemonToList(pokemonResource)
                if (listPokemon?.size == LIMIT) {
                    listPokemon?.let {
                        val pokemons = ListPokemon(it)
                        onPokemonListReady?.invoke(pokemons)
                        listPokemon = null
                    }
                }
            }
            is PokemonSpecies -> {
                pokemonDetail?.pokemonSpecies = pokemonResource
                pokemon?.abilityNames?.forEach {
                    remotePokemonSource.callPokemonAbility(it)
                }
            }
            is Ability -> {
                pokemonDetail?.abilities?.apply {
                    add(pokemonResource)
                    if (size == pokemon?.abilityNames?.size) {
                        pokemon?.typeNames?.forEach {
                            remotePokemonSource.callPokemonType(it)
                        }
                    }
                }
            }
            is Type -> {
                pokemonDetail?.types?.apply {
                    add(pokemonResource)
                    if (size == pokemon?.typeNames?.size) {
                        pokemonDetail?.let {
                            onPokemonDetailReady?.invoke(it)
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
            Log.e(null, message)
        }
        pokemonDetail = null
        pokemon = null
        onServiceFinishedError?.invoke()
    }

    companion object {
        private const val OFFSET = 0
        private const val LIMIT = 30
    }
}