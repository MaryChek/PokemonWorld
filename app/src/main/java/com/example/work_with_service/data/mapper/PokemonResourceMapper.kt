package com.example.work_with_service.data.mapper

import com.example.work_with_service.domain.entities.Pokemon as DomainPokemon
import com.example.work_with_service.domain.entities.Type as DomainPokemonType
import com.example.work_with_service.domain.entities.Ability as DomainPokemonAbility
import com.example.work_with_service.domain.entities.PokemonSpecies as DomainPokemonSpecies
import com.example.work_with_service.data.model.NameApiResource
import com.example.work_with_service.data.model.Pokemon
import com.example.work_with_service.data.model.PokemonSpecies
import com.example.work_with_service.data.model.Ability
import com.example.work_with_service.data.model.Type

class PokemonResourceMapper {
    fun map(resource: List<Pokemon>): List<DomainPokemon> =
        resource.map {
            DomainPokemon(
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

    fun map(resource: PokemonSpecies): DomainPokemonSpecies =
        resource.let {
            DomainPokemonSpecies(
                it.isBaby,
                it.habitat.name,
                it.color.name,
                it.captureRate
            )
        }

    fun map(resource: Ability): DomainPokemonAbility =
        resource.let {
            DomainPokemonAbility(
                it.name,
                it.effectEntries.map { verboseEffect ->
                    DomainPokemonAbility.Effect(
                        verboseEffect.effect,
                        verboseEffect.language.name
                    )
                })
        }

    fun map(resource: Type): DomainPokemonType =
        resource.let {
            DomainPokemonType(
                it.name,
                it.damageRelations.let { typeRelations ->
                    DomainPokemonType.DamageTypes(
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