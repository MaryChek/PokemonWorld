package com.example.work_with_service.data.client

import com.example.work_with_service.di.entities.PokemonResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class PokemonRemoteDataSource {
    fun <T : PokemonResource> getResult(
        call: Call<T>,
        onServiceCallAnswer: (Resource<PokemonResource>) -> Unit
    ) {
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