package com.example.work_with_service.presentation.mappers

import com.example.work_with_service.domain.models.Pokemon as DomainPokemon
import com.example.work_with_service.presentation.models.Pokemon
import com.example.work_with_service.domain.models.Status as DomainStatus
import com.example.work_with_service.presentation.models.Status

open class PokemonListMapper : BasePokemonMapper() {

    fun mapStatus(status: DomainStatus, errorMessage: String?): Status =
        when (status) {
            DomainStatus.SUCCESS -> Status.success()
            DomainStatus.ERROR -> Status.error(errorMessage)
        }

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