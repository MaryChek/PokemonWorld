package com.example.work_with_service.model

import androidx.lifecycle.LiveData

interface PokemonDataAccess {
    fun getPokemonLinks() : LiveData<PokemonApiResourceList>

    fun getListOfPokemon(name: String) : LiveData<List<Pokemon>>

    suspend fun insertAll(characters: List<Pokemon>)
}