package com.example.work_with_service.ui.model

import com.example.work_with_service.di.entities.Ability
import com.example.work_with_service.di.entities.Pokemon
import com.example.work_with_service.di.entities.PokemonSpecies
import com.example.work_with_service.di.entities.Type

sealed class ServicePokemonAnswer

class ListPokemon(
    val listPokemon: List<Pokemon>
) : ServicePokemonAnswer()

class PokiInfo : ServicePokemonAnswer() {
    lateinit var pokemonSpecies: PokemonSpecies
    val types: MutableList<Type> = mutableListOf()
    val abilities: MutableList<Ability> = mutableListOf()
}