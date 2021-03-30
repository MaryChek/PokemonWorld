package com.example.work_with_service.ui.model

import com.example.work_with_service.data.entities.Ability
import com.example.work_with_service.data.entities.Pokemon
import com.example.work_with_service.data.entities.PokemonSpecies
import com.example.work_with_service.data.entities.Type

sealed class ServicePokemonAnswer

class ListPokemon(
    val listPokemon: List<Pokemon>
) : ServicePokemonAnswer()

class PokiDetail(
    val name: String,
    val baseExperience: Int,
    val height: Int,
    val weight: Int
) : ServicePokemonAnswer() {
    lateinit var pokemonSpecies: PokemonSpecies
    val types: MutableList<Type> = mutableListOf()
    val abilities: MutableList<Ability> = mutableListOf()
}