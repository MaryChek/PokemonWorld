package com.example.work_with_service.model

sealed class ServicePokemonAnswer

class ListPokemon(
    val listPokemon: List<Pokemon>
) : ServicePokemonAnswer()

class PokiInfo
: ServicePokemonAnswer() {
    lateinit var pokemonSpecies: PokemonSpecies
    var type: MutableList<Type> = mutableListOf()
    var ability: MutableList<Ability> = mutableListOf()
}