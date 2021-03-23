package com.example.work_with_service.client

import com.example.work_with_service.model.PokemonResource
import com.example.work_with_service.model.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokeApiClient(
    private val onServiceCallAnswer: (Resource<PokemonResource>) -> Unit,
    clientConfig: ClientConfig = ClientConfig()
) {
    private val service = PokeApiServiceRetrofit(clientConfig)

    fun callPokemonResourceList(offset: Int, limit: Int) =
        getResult(service.client.getPokemonList(offset, limit))

    fun callPokemon(name: String) =
        getResult(service.client.getPokemon(name))

    fun callPokemonSpecies(name: String) =
        getResult(service.client.getPokemonSpecies(name))

    fun callPokemonAbility(name: String) =
        getResult(service.client.getPokemonAbility(name))

    fun callPokemonType(name: String) =
        getResult(service.client.getPokemonType(name))

    private fun <T : PokemonResource> getResult(call: Call<T>) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val pokemonResource: T? = response.body()
                onServiceCallAnswer(
                    if (response.isSuccessful && pokemonResource != null) {
                        Resource.success(pokemonResource)
                    } else {
                        error(" ${response.code()} ${response.message()}")
                    }
                )
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                onServiceCallAnswer(error(t.message))
            }
        })
    }

    private fun error(message: String?): Resource<PokemonResource> =
        Resource.error("Network call has failed for a following reason: $message")
}