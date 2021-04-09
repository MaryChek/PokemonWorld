package com.example.work_with_service.ui.mapper

import com.example.work_with_service.R
import com.example.work_with_service.data.model.Ability
import com.example.work_with_service.ui.model.pokiattributes.PokemonDetail
import com.example.work_with_service.ui.model.servicepokemonanswer.PokiDetail

open class PokemonDetailMapper : BasePokemonMapper() {
    fun map(pokiDetail: PokiDetail): PokemonDetail =
        pokiDetail.let {
            PokemonDetail(
                it.frontDefault,
                it.name,
                it.baseExperience,
                it.pokemonSpecies.captureRate,
                it.height.times(10),
                it.weight.div(100.0),
                when (it.pokemonSpecies.isBaby) {
                    true -> R.string.baby
                    false -> R.string.adult
                },
                firstUpperCase(it.pokemonSpecies.habitat),
                firstUpperCase(it.pokemonSpecies.color),
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

    private fun mapTypes(pokiDetail: PokiDetail): List<PokemonDetail.Type> =
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