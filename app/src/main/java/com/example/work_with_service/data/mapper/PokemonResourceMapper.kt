package com.example.work_with_service.data.mapper

import com.example.work_with_service.data.model.PokemonResource
import com.example.work_with_service.data.model.Pokemon
import com.example.work_with_service.data.model.Type
import com.example.work_with_service.data.model.Ability
import com.example.work_with_service.data.model.PokemonSpecies
import com.example.work_with_service.data.model.PokemonResourceList
import com.example.work_with_service.domain.entities.NameApiResource
import com.example.work_with_service.domain.entities.PokemonResourceList as DomainPokemonResourceList
import com.example.work_with_service.domain.entities.Pokemon as DomainPokemon
import com.example.work_with_service.domain.entities.PokemonSpecies as DomainPokemonSpecies
import com.example.work_with_service.domain.entities.Ability as DomainPokemonAbility
import com.example.work_with_service.domain.entities.Type as DomainPokemonType

class PokemonResourceMapper {

    fun <T> map(domainPokemonResource: T): PokemonResource? =
        when (domainPokemonResource) {
            is DomainPokemonResourceList -> map(domainPokemonResource)
            is DomainPokemon -> map(domainPokemonResource)
            is DomainPokemonSpecies -> map(domainPokemonResource)
            is DomainPokemonAbility -> map(domainPokemonResource)
            is DomainPokemonType -> map(domainPokemonResource)
            else -> null
        }

    private fun map(resource: DomainPokemonResourceList): PokemonResourceList =
        PokemonResourceList(
            mapNameApiResource(resource.results)
        )

    private fun map(resource: DomainPokemon): Pokemon =
        resource.let {
            Pokemon(
                it.name,
                it.baseExperience,
                it.height,
                it.weight,
                it.abilities.map { ability ->
                    ability.ability.name
                },
                it.types.map { type ->
                    type.type.name
                },
                it.sprites.frontDefault
            )
        }

    private fun map(resource: DomainPokemonSpecies): PokemonSpecies =
        resource.let {
            PokemonSpecies(
                it.isBaby,
                it.habitat.name,
                it.color.name,
                it.captureRate
            )
        }

    private fun map(resource: DomainPokemonAbility): Ability =
        resource.let {
            Ability(
                it.name,
                it.effectEntries.map { verboseEffect ->
                    Ability.Effect(
                        verboseEffect.effect,
                        verboseEffect.language.name
                    )
                })
        }

    private fun map(resource: DomainPokemonType): Type =
        resource.let {
            Type(
                it.name,
                it.damageRelations.let { typeRelations ->
                    Type.DamageTypes(
                        mapNameApiResource(typeRelations.noDamageTo),
                        mapNameApiResource(typeRelations.doubleDamageTo),
                        mapNameApiResource(typeRelations.noDamageFrom),
                        mapNameApiResource(typeRelations.doubleDamageFrom)
                    )
                })
        }

    private fun mapNameApiResource(nameApiResource: List<NameApiResource>): List<String> =
        nameApiResource.map {
            it.name
        }
}