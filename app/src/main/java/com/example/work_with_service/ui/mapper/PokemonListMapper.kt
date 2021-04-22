package com.example.work_with_service.ui.mapper

import com.example.work_with_service.data.model.ListPokemon
import com.example.work_with_service.ui.model.Pokemon
import com.example.work_with_service.ui.model.PokemonsAttributes.Attributes

open class PokemonListMapper : BasePokemonMapper() {
    fun map(listPokemon: List<Pokemon>): List<Attributes> =
        listPokemon.map {
            Attributes(it.frontDefault, firstUpperCase(it.name))
        }

    fun map(pokemonAnswer: ListPokemon): List<Pokemon> =
        pokemonAnswer.listPokemon.map {
            Pokemon(
                it.name,
                it.baseExperience,
                it.height,
                it.weight,
                it.abilityNames,
                it.typeNames,
                it.frontDefault
            )
        }
}