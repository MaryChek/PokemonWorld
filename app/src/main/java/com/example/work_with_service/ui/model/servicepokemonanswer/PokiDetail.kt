package com.example.work_with_service.ui.model.servicepokemonanswer

import com.example.work_with_service.data.model.Ability
import com.example.work_with_service.data.model.PokemonSpecies
import com.example.work_with_service.data.model.Type

class PokiDetail(
    val frontDefault: String,
    val name: String,
    val baseExperience: Int,
    val height: Int,
    val weight: Int
) : ServicePokemonAnswer {
    lateinit var pokemonSpecies: PokemonSpecies
    val types: MutableList<Type> = mutableListOf()
    val abilities: MutableList<Ability> = mutableListOf()
}