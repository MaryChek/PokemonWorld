package com.example.work_with_service.di.entities

sealed class PokemonResource

class PokemonResourceList(
    val count: Int,
    val next: String,
    val results: List<NameResource>
) : PokemonResource()

class Pokemon(
    val name: String,
    val baseExperience: Int,
    val height: Int,
    val weight: Int,
    val abilities: List<PokemonAbility>,
    val types: List<PokemonType>,
    val sprites: PokemonSprites
) : PokemonResource()

class PokemonAbility(
    val ability: NameResource
)

class PokemonType(
    val type: NameResource
)

class PokemonSprites(
    val frontDefault: String
)

class PokemonSpecies(
    val isBaby: Boolean,
    val habitat: NameResource,
    val color: NameResource,
    val captureRate: Int
) : PokemonResource()

class Ability(
    val name: String,
    val effectEntries: List<VerboseEffect>
) : PokemonResource()

class VerboseEffect(
    val effect: String,
    val language: NameResource
)

class Type(
    val name: String,
    val damageRelations: TypeRelations
) : PokemonResource()

class TypeRelations(
    val noDamageTo: List<NameResource>,
    val doubleDamageTo: List<NameResource>,
    val noDamageFrom: List<NameResource>,
    val doubleDamageFrom: List<NameResource>
)

class NameResource(
    val name: String
)