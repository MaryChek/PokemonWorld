package com.example.work_with_service.data.model

class PokemonDetail {
    var species: PokemonSpecies? = null
    var abilities: MutableList<Ability> = mutableListOf()
    var types: MutableList<Type> = mutableListOf()
}
