package com.example.work_with_service.data.api

import com.example.work_with_service.data.api.model.*
import com.example.work_with_service.domain.models.Resource

class PokemonApiTalker(
    clientConfig: ClientConfig,
    service: ClientPokemonApiCreator,
    private val client: PokemonApi = service.createPokemonApiClient(clientConfig)
) : BaseApiTalker() {

    fun callPokemonResourceList(
        offset: Int,
        limit: Int,
        onServiceCallAnswer: (Resource<PokemonResourceList>) -> Unit
    ) =
        getResult(client.getPokemonList(offset, limit), onServiceCallAnswer)

    fun callPokemon(name: String, onServiceCallAnswer: (Resource<Pokemon>) -> Unit) =
        getResult(client.getPokemon(name), onServiceCallAnswer)

    fun callPokemonSpecies(name: String, onServiceCallAnswer: (Resource<PokemonSpecies>) -> Unit) =
        getResult(client.getPokemonSpecies(name), onServiceCallAnswer)

    fun callPokemonAbility(name: String, onServiceCallAnswer: (Resource<Ability>) -> Unit) =
        getResult(client.getPokemonAbility(name), onServiceCallAnswer)

    fun callPokemonType(name: String, onServiceCallAnswer: (Resource<Type>) -> Unit) =
        getResult(client.getPokemonType(name), onServiceCallAnswer)
}