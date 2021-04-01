package com.example.work_with_service.ui.model

import com.example.work_with_service.data.entities.Ability
import com.example.work_with_service.data.entities.Pokemon
import com.example.work_with_service.data.entities.PokemonSpecies
import com.example.work_with_service.data.entities.Type
import com.example.work_with_service.data.service.Status.*
import com.example.work_with_service.data.service.Status

open class ServicePokemonAnswer

class ListPokemon(
    val listPokemon: List<Pokemon>
) : ServicePokemonAnswer()

class PokiInfo : ServicePokemonAnswer() {
    lateinit var pokemonSpecies: PokemonSpecies
    val types: MutableList<Type> = mutableListOf()
    val abilities: MutableList<Ability> = mutableListOf()
}