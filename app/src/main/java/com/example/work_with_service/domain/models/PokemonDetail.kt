package com.example.work_with_service.domain.models

class PokemonDetail(
    val name: String,
    val species: PokemonSpecies,
    var abilities: MutableList<Ability> = mutableListOf(),
    var types: MutableList<Type> = mutableListOf()
)