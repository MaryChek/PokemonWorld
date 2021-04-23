package com.example.work_with_service.ui.mapper

import com.example.work_with_service.domain.entities.Pokemon as DomainPokemon
import com.example.work_with_service.ui.model.Pokemon

open class PokemonListMapper : BasePokemonMapper() {
    fun map(pokemonAnswer: List<DomainPokemon>): List<Pokemon> =
        pokemonAnswer.map {
            Pokemon(
                firstUpperCase(it.name),
                it.baseExperience,
                it.height.times(10),
                it.weight.div(100.0),
                it.abilityNames,
                it.typeNames,
                it.imageUrl
            )
        }
}