package com.example.work_with_service.data.mappers

import com.example.work_with_service.data.api.model.*
import com.example.work_with_service.domain.models.Pokemon as DomainPokemon
import com.example.work_with_service.domain.models.Type as DomainPokemonType
import com.example.work_with_service.domain.models.Ability as DomainPokemonAbility
import com.example.work_with_service.domain.models.PokemonSpecies as DomainPokemonSpecies

class PokemonResourceMapper {
    fun mapPokemonResourceList(resource: PokemonResourceList?): List<String>? =
        resource?.results?.map {
            it.name
        }

    fun map(resource: List<Pokemon>): List<DomainPokemon> =
        resource.mapNotNull(this::mapPokemon)

    fun mapPokemon(resource: Pokemon?): DomainPokemon? =
        resource?.let {
            DomainPokemon(
                resource.name,
                resource.baseExperience,
                resource.height,
                resource.weight,
                resource.abilities.map { ability ->
                    ability.ability.name
                },
                resource.types.map { type ->
                    type.type.name
                },
                resource.sprites.frontDefault
            )
        }

    fun mapSpecies(resource: PokemonSpecies?): DomainPokemonSpecies? =
        resource?.let {
            DomainPokemonSpecies(
                it.isBaby,
                it.habitat.name,
                it.color.name,
                it.captureRate
            )
        }

    fun mapAbility(resource: Ability?): DomainPokemonAbility? =
        resource?.let {
            DomainPokemonAbility(
                it.name,
                it.effectEntries.map { verboseEffect ->
                    DomainPokemonAbility.Effect(
                        verboseEffect.effect,
                        verboseEffect.language.name
                    )
                })
        }

    fun mapType(resource: Type?): DomainPokemonType? =
        resource?.let {
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