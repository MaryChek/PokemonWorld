package com.example.work_with_service.data.model

class PokemonDetail(
    val species: PokemonSpecies,
    var abilities: MutableList<Ability> = mutableListOf(),
    var types: MutableList<Type> = mutableListOf()
)
