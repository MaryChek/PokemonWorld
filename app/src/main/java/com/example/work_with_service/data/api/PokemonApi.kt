package com.example.work_with_service.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.work_with_service.data.api.model.PokemonResourceList
import com.example.work_with_service.data.api.model.Pokemon
import com.example.work_with_service.data.api.model.PokemonSpecies
import com.example.work_with_service.data.api.model.Ability
import com.example.work_with_service.data.api.model.Type

interface PokemonApi {
    @GET("/api/v2/pokemon")
    fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Call<PokemonResourceList>

    @GET("/api/v2/pokemon/{name}")
    fun getPokemon(@Path("name") name: String?): Call<Pokemon>

    @GET("/api/v2/pokemon-species/{name}")
    fun getPokemonSpecies(@Path("name") name: String?): Call<PokemonSpecies>

    @GET("/api/v2/ability/{name}")
    fun getPokemonAbility(@Path("name") name: String?): Call<Ability>

    @GET("/api/v2/type/{name}")
    fun getPokemonType(@Path("name") name: String?): Call<Type>
}