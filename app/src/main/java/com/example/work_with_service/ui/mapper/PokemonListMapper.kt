package com.example.work_with_service.ui.mapper

import com.example.work_with_service.data.model.ListPokemon
import com.example.work_with_service.ui.model.Pokemon
import com.example.work_with_service.ui.model.pokemons.PokemonsAttributes.Attribute

open class PokemonListMapper : BasePokemonMapper() {
    fun map(listPokemon: List<Pokemon>): List<Attribute> =
        listPokemon.map {
            Attribute(it.frontDefault, it.name)
        }

    fun map(pokemonAnswer: ListPokemon): List<Pokemon> =
        pokemonAnswer.listPokemon.map {
            Pokemon(
                firstUpperCase(it.name),
                it.baseExperience,
                it.height.times(10),
                it.weight.div(100.0),
                it.abilityNames,
                it.typeNames,
                it.frontDefault
            )
        }
}