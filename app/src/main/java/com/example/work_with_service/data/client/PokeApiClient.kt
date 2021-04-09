package com.example.work_with_service.data.client

import com.example.work_with_service.data.model.*
import com.example.work_with_service.data.service.PokeApiServiceRetrofit
import com.example.work_with_service.data.service.Resource

class PokeApiClient(
    private val onServiceCallAnswer: (Resource<PokemonResource?>) -> Unit,
    clientConfig: ClientConfig = ClientConfig()
) : PokemonRemoteDataSource() {
    private val service = PokeApiServiceRetrofit(clientConfig)

    fun callPokemonResourceList(offset: Int, limit: Int) =
        getResult(service.client.getPokemonList(offset, limit), onServiceCallAnswer)

    fun callPokemon(name: String) =
        getResult(service.client.getPokemon(name), onServiceCallAnswer)

    fun callPokemonSpecies(name: String) =
        getResult(service.client.getPokemonSpecies(name), onServiceCallAnswer)

    fun callPokemonAbility(name: String) =
        getResult(service.client.getPokemonAbility(name), onServiceCallAnswer)

    fun callPokemonType(name: String) =
        getResult(service.client.getPokemonType(name), onServiceCallAnswer)
}