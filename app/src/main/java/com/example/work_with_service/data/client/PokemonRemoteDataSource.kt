package com.example.work_with_service.data.client

import com.example.work_with_service.data.mapper.Mapper
import com.example.work_with_service.data.model.*
import com.example.work_with_service.data.service.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class PokemonRemoteDataSource {
    private val mapper = Mapper()

    fun <T> getResult(
        call: Call<T>,
        onServiceCallAnswer: (Resource<PokemonResource?>) -> Unit
    ) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val pokemonResource: T? = response.body()
                onServiceCallAnswer(
                    if (response.isSuccessful && pokemonResource != null) {
                        Resource.success(mapper.map(pokemonResource))
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

    private fun <T> error(message: String?): Resource<T> =
        Resource.error(NETWORK_FAIL + message)

    companion object {
        private const val NETWORK_FAIL = "Network call has failed for a following reason: "
    }
}

