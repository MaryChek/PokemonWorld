package com.example.work_with_service.presentation.mappers

import com.example.work_with_service.domain.models.PokemonDetail as DomainPokemonDetail
import com.example.work_with_service.domain.models.Pokemon as DomainPokemon
import com.example.work_with_service.domain.models.Ability
import com.example.work_with_service.presentation.models.Pokemon
import com.example.work_with_service.presentation.models.PokemonDetail

open class PokemonDetailMapper : BasePokemonMapper() {

    fun map(pokemon: Pokemon): DomainPokemon =
        DomainPokemon(
            firstLowerCase(pokemon.name),
            pokemon.baseExperience,
            pokemon.height,
            pokemon.weight.toInt(),
            pokemon.abilityNames,
            pokemon.typeNames,
            pokemon.imageUrl
        )

    fun map(pokemonDetail: DomainPokemonDetail): PokemonDetail =
        pokemonDetail.let {
            PokemonDetail(
//                firstUpperCase(it.name),
                it.species.captureRate,
                it.species.isBaby,
                firstUpperCase(it.species.habitat),
                firstUpperCase(it.species.color),
                mapAbilities(pokemonDetail.abilities),
                mapTypes(pokemonDetail)
            )
        }

    private fun mapAbilities(abilities: List<Ability>): List<PokemonDetail.Ability> =
        abilities.mapNotNull { ability ->
            ability.effects.firstOrNull {
                it.language == ENGLISH_LANGUAGE
            }?.let {
                PokemonDetail.Ability(
                    firstUpperCase(ability.name), it.description.replace(TWO_NL, PARAGRAPH)
                )
            }
        }

    private fun mapTypes(pokiDetail: DomainPokemonDetail): List<PokemonDetail.Type> =
        pokiDetail.types.map { type ->
            PokemonDetail.Type(
                firstUpperCase(type.name),
                type.damageTypes.noDamageTo.map {
                    firstUpperCase(it)
                },
                type.damageTypes.doubleDamageTo.map {
                    firstUpperCase(it)
                },
                type.damageTypes.noDamageFrom.map {
                    firstUpperCase(it)
                },
                type.damageTypes.doubleDamageFrom.map {
                    firstUpperCase(it)
                }
            )
        }

    companion object {
        private const val ENGLISH_LANGUAGE = "en"
        private const val TWO_NL = "\n\n"
        private const val PARAGRAPH = "\n\t\t\t"
    }
}