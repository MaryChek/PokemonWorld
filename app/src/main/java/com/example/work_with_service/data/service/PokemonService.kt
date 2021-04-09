package com.example.work_with_service.data.service

import android.util.Log
import com.example.work_with_service.data.client.PokeApiClient
import com.example.work_with_service.data.model.*
import com.example.work_with_service.data.model.ListPokemon
import com.example.work_with_service.data.service.Status.*

class PokemonService {
    private var onPokemonListReady: ((ListPokemon) -> Unit)? = null
    private var onPokemonDetailReady: ((PokemonDetail) -> Unit)? = null
    private var onServiceFinishedError: (() -> Unit)? = null
    private val remotePokemonSource = PokeApiClient(this::onServiceCallAnswer)

    //    private var pokemon: Pokemon? = null

    //    private var pokemonDetail: PokiDetail? = null
//    private var pokemonAbilities: MutableList<Ability> = mutableListOf()
//    private var pokemonTypes: MutableList<Type> = mutableListOf()
    private var listPokemon: MutableList<Pokemon>? = null
    private var pokemonDetail: PokemonDetail? = null
    private var pokemonAbilities: List<String> = listOf()
    private var pokemonTypes: List<String> = listOf()

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
        pokemonName: String,
        abilityNames: List<String>,
        typeNames: List<String>,
        onPokemonDetailReady: (PokemonDetail) -> Unit,
        onServiceFinishedError: () -> Unit
    ) {
        this.onPokemonDetailReady = onPokemonDetailReady
        this.onServiceFinishedError = onServiceFinishedError
        pokemonDetail = PokemonDetail()
        pokemonAbilities = abilityNames
        pokemonTypes = typeNames
        callPokemonSpecies(pokemonName)
//        callPokemonAbilities(abilityNames)
//        callPokemonTypes(typeNames)
    }

    private fun callPokemonSpecies(pokemonName: String) =
        remotePokemonSource.callPokemonSpecies(pokemonName)

    private fun callPokemonAbilities() {
        pokemonAbilities.forEach {
            remotePokemonSource.callPokemonAbility(it)
        }
    }

    private fun callPokemonTypes() {
        pokemonTypes.forEach {
            remotePokemonSource.callPokemonType(it)
        }
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
                listPokemon?.let {
                    if (it.size == LIMIT) {
                        onPokemonListReady?.invoke(ListPokemon(it))
                        listPokemon = null
                    }
                }
            }
            is PokemonSpecies -> {
                pokemonDetail?.species = pokemonResource
                callPokemonAbilities()
//                pokemonDetailReady()
//                pokemonDetail?.pokemonSpecies = pokemonResource
//                pokemon?.abilityNames?.forEach {
//                    remotePokemonSource.callPokemonAbility(it)
//                }
            }
            is Ability -> {
                pokemonDetail?.abilities?.let {
                    it.add(pokemonResource)
                    if (it.size == pokemonAbilities.size) {
                        callPokemonTypes()
                    }
//                pokemonDetailReady()
//                pokemonDetail?.abilities?.apply {
//                    add(pokemonResource)
//                    if (size == pokemon?.abilityNames?.size) {
//                        pokemon?.typeNames?.forEach {
//                            remotePokemonSource.callPokemonType(it)
//                        }
//                    }
//                }

                }
            }
            is Type -> {
                pokemonDetail?.types?.let { types ->
                    types.add(pokemonResource)
                    if (types.size == pokemonTypes.size) {
                        pokemonDetail?.let {
                            onPokemonDetailReady?.invoke(it)
                        }
                    }
//                    pokemonDetailReady()
//                pokemonDetail?.types?.apply {
//                    add(pokemonResource)
//                    if (size == pokemon?.typeNames?.size) {
//                        pokemonDetail?.let {
//                            onPokemonDetailReady?.invoke(it)
//                            pokemonDetail = null
//                        }
//                        pokemon = null
//                    }
//                }
                }
            }
        }
    }

//    private fun isPokemonDetailReady(pokemonDetail: PokemonDetail): Boolean =
//        pokemonDetail.abilities.size == countPokemonAbility
//            && pokemonDetail.types.size == countPokemonType
//            && pokemonDetail.species != null
//
//    private fun pokemonDetailReady() {
//        pokemonDetail?.let {
//            if (isPokemonDetailReady(it)) {
//                onPokemonDetailReady?.invoke(it)
//                countPokemonType = 0
//                countPokemonAbility = 0
//            }
//        }
//    }

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
    }
}