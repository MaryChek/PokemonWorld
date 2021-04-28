package com.example.work_with_service.data.client

import com.example.work_with_service.data.model.*
import com.example.work_with_service.data.service.PokeApiService
import com.example.work_with_service.data.service.PokeApiServiceRetrofit
import com.example.work_with_service.data.service.Resource

class PokeApiClient(
    clientConfig: ClientConfig,
    service: PokeApiServiceRetrofit,
    private val client: PokeApiService = service.createPokeApiClient(clientConfig)
) : PokemonRemoteDataSource() {

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