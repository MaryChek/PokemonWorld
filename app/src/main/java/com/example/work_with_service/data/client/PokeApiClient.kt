package com.example.work_with_service.data.client

import com.example.work_with_service.data.model.*
import com.example.work_with_service.data.service.PokeApiServiceRetrofit
import com.example.work_with_service.data.service.Resource

class PokeApiClient(clientConfig: ClientConfig = ClientConfig()) : PokemonRemoteDataSource() {
    private val service = PokeApiServiceRetrofit(clientConfig)

    fun callPokemonResourceList(
        offset: Int,
        limit: Int,
        onServiceCallAnswer: (Resource<PokemonResourceList>) -> Unit
    ) =
        getResult(service.client.getPokemonList(offset, limit), onServiceCallAnswer)

    fun callPokemon(name: String, onServiceCallAnswer: (Resource<Pokemon>) -> Unit) =
        getResult(service.client.getPokemon(name), onServiceCallAnswer)

    fun callPokemonSpecies(name: String, onServiceCallAnswer: (Resource<PokemonSpecies>) -> Unit) =
        getResult(service.client.getPokemonSpecies(name), onServiceCallAnswer)

    fun callPokemonAbility(name: String, onServiceCallAnswer: (Resource<Ability>) -> Unit) =
        getResult(service.client.getPokemonAbility(name), onServiceCallAnswer)

    fun callPokemonType(name: String, onServiceCallAnswer: (Resource<Type>) -> Unit) =
        getResult(service.client.getPokemonType(name), onServiceCallAnswer)
}