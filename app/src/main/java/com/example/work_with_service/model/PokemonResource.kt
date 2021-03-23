package com.example.work_with_service.model

import org.intellij.lang.annotations.Language

sealed class PokemonResource

class PokemonApiResourceList(
    val count: Int,
    val next: String,
    val previous: Boolean,
    val results: List<NamedApiResource>
) : PokemonResource()

class Pokemon(
    val name: String, //y
    val baseExperience: Int, //y
    val height: Int, //y
    val weight: Int, //y
    val species: NamedApiResource,
    val abilities: List<PokemonAbility>,
    val types: List<PokemonType>,
    val sprites: PokemonSprites //y
) : PokemonResource()

class PokemonAbility(
    val ability: NamedApiResource
)

class PokemonType(
    val type: NamedApiResource
)

class PokemonSprites(
    val backDefault: String,
    val backShiny: String,
    val frontDefault: String,
    val frontShiny: String,
    val backFemale: String,
    val backShinyFemale: String,
    val frontFemale: String,
    val frontShinyFemale: String
)

class PokemonSpecies(
    val isBaby: Boolean ,
    val habitat: NamedApiResource,
    val color: NamedApiResource,
    val captureRate: Int
) : PokemonResource()

class Ability(
    val name: String,
    val effectEntries: List<VerboseEffect>
) : PokemonResource()

class VerboseEffect(
    val effect: String,
    val shortEffect: String,
    val language: NamedApiResource
)

class Type(
    val name: String,
    val damageRelations: TypeRelations
) : PokemonResource()

class TypeRelations(
     val noDamageTo: List<NamedApiResource>,
     val doubleDamageTo: List<NamedApiResource>,
     val noDamageFrom: List<NamedApiResource>,
     val doubleDamageFrom: List<NamedApiResource>
)

class NamedApiResource(
    val name: String,
    val url: String
)