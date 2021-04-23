package com.example.work_with_service.data.repository

import android.util.Log
import com.example.work_with_service.data.client.PokeApiClient
import com.example.work_with_service.data.model.PokemonDetail
import com.example.work_with_service.data.model.ListPokemon
import com.example.work_with_service.data.model.PokemonResource
import com.example.work_with_service.data.model.PokemonResourceList
import com.example.work_with_service.data.model.Pokemon
import com.example.work_with_service.data.model.PokemonSpecies
import com.example.work_with_service.data.model.Ability
import com.example.work_with_service.data.model.Type
import com.example.work_with_service.data.service.Resource
import com.example.work_with_service.data.service.Status.SUCCESS

class PokemonRepository(private val remotePokemonSource: PokeApiClient = PokeApiClient()) {
    private var onPokemonListReady: ((ListPokemon) -> Unit)? = null
    private var onPokemonDetailReady: ((PokemonDetail) -> Unit)? = null
    private var onServiceFinishedError: (() -> Unit)? = null
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
        if (isServiceCallSuccess(resource)) {
            resource.data?.let {
                it.pokemonNames.forEach { namePokemon ->
                    remotePokemonSource.callPokemon(namePokemon, this::onServiceSendPokemon)
                }
            }
        }
    }

    private fun onServiceSendPokemon(resource: Resource<Pokemon>) {
        if (isServiceCallSuccess(resource)) {
            resource.data?.let { pokemon ->
                addPokemonToList(pokemon)
                listPokemon?.let {
                    if (it.size == LIMIT) {
                        onPokemonListReady?.invoke(ListPokemon(it))
                        listPokemon = null
                    }
                }
            }
        }
    }

    private fun onServiceSendPokemonSpecies(resource: Resource<PokemonSpecies>) {
        if (isServiceCallSuccess(resource)) {
            resource.data?.let {
                pokemonDetail = PokemonDetail(it)
                callPokemonAbilities()
            }
        }
    }

    private fun onServiceSendPokemonAbility(resource: Resource<Ability>) {
        if (isServiceCallSuccess(resource)) {
            resource.data?.let { ability ->
                pokemonDetail?.abilities?.let {
                    it.add(ability)
                    if (it.size == pokemonAbilities.size) {
                        callPokemonTypes()
                    }
                }
            }
        }
    }

    private fun onServiceSendPokemonType(resource: Resource<Type>) {
        if (isServiceCallSuccess(resource)) {
            resource.data?.let { type ->
                pokemonDetail?.types?.let { types ->
                    types.add(type)
                    if (types.size == pokemonTypes.size) {
                        pokemonDetail?.let {
                            onPokemonDetailReady?.invoke(it)
                        }
                    }
                }
            }
        }
    }

    private fun <T> isServiceCallSuccess(res: Resource<T>): Boolean =
        // mapper get resource data
        if (res.status == SUCCESS) {
            true
        } else {
            onResponseError(res.message)
            false
        }


//    private fun onResponseSuccess(pokemonResource: PokemonResource?) {
//        when (pokemonResource) {
//            is PokemonResourceList -> {
//                pokemonResource.pokemonNames.forEach {
//                    remotePokemonSource.callPokemon(it)
//                }
//            }
//            is Pokemon -> {
//                addPokemonToList(pokemonResource)
//                listPokemon?.let {
//                    if (it.size == LIMIT) {
//                        onPokemonListReady?.invoke(ListPokemon(it))
//                        listPokemon = null
//                    }
//                }
//            }
//            is PokemonSpecies -> {
//                pokemonDetail = PokemonDetail(pokemonResource)
//                callPokemonAbilities()
//            }
//            is Ability -> {
//                pokemonDetail?.abilities?.let {
//                    it.add(pokemonResource)
//                    if (it.size == pokemonAbilities.size) {
//                        callPokemonTypes()
//                    }
//                }
//            }
//            is Type -> {
//                pokemonDetail?.types?.let { types ->
//                    types.add(pokemonResource)
//                    if (types.size == pokemonTypes.size) {
//                        pokemonDetail?.let {
//                            onPokemonDetailReady?.invoke(it)
//                        }
//                    }
//                }
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

        private var INSTANCE: PokemonRepository? = null

        fun getInstance(): PokemonRepository =
            INSTANCE ?: PokemonRepository().also {
                INSTANCE = it
            }
    }
}