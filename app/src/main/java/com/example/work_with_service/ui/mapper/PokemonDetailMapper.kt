package com.example.work_with_service.ui.mapper

import com.example.work_with_service.data.model.PokemonDetail as DataPokemonDetail
import com.example.work_with_service.R
import com.example.work_with_service.data.model.Ability
import com.example.work_with_service.ui.model.Pokemon
import com.example.work_with_service.ui.model.PokemonDetail

open class PokemonDetailMapper : BasePokemonMapper() {
    fun map(pokiDetail: DataPokemonDetail, pokemon: Pokemon): PokemonDetail =
        pokiDetail.let {
            PokemonDetail(
                pokemon.frontDefault,
                firstUpperCase(pokemon.name),
                pokemon.baseExperience,
                it.species.captureRate,
                pokemon.height.times(10),
                pokemon.weight.div(100.0),
                when (it.species.isBaby) {
                    true -> R.string.baby
                    false -> R.string.adult
                },
                firstUpperCase(it.species.habitat),
                firstUpperCase(it.species.color),
                mapAbilities(pokiDetail.abilities),
                mapTypes(pokiDetail)
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

    private fun mapTypes(pokiDetail: DataPokemonDetail): List<PokemonDetail.Type> =
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