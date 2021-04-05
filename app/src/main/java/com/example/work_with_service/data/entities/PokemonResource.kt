package com.example.work_with_service.data.entities

import com.squareup.moshi.Json
import java.io.Serializable

sealed class PokemonResource {
    class NameResource(val name: String)
}

class PokemonResourceList(
    val count: Int,
    val next: String,
    val results: List<NameResource>
) : PokemonResource()

class Pokemon(
    val name: String,
    @field:Json(name = "base_experience")
    val baseExperience: Int,
    val height: Int,
    val weight: Int,
    val abilities: List<PokemonAbility>,
    val types: List<PokemonType>,
    val sprites: PokemonSprites
) : PokemonResource(), Serializable {

    class PokemonAbility(val ability: NameResource)

    class PokemonType(val type: NameResource)

    class PokemonSprites(
        @field:Json(name = "front_default")
        val frontDefault: String
    )
}

class PokemonSpecies(
    @field:Json(name = "is_Baby")
    val isBaby: Boolean,
    val habitat: NameResource,
    val color: NameResource,
    @field:Json(name = "capture_rate")
    val captureRate: Int
) : PokemonResource()

class Ability(
    val name: String,
    @field:Json(name = "effect_entries")
    val effectEntries: List<VerboseEffect>
) : PokemonResource() {

    class VerboseEffect(val effect: String, val language: NameResource)
}

class Type(
    val name: String,
    @field:Json(name = "damage_relations")
    val damageRelations: TypeRelations
) : PokemonResource() {

    class TypeRelations(
        @field:Json(name = "no_damage_to")
        val noDamageTo: List<NameResource>,
        @field:Json(name = "double_damage_to")
        val doubleDamageTo: List<NameResource>,
        @field:Json(name = "no_damage_from")
        val noDamageFrom: List<NameResource>,
        @field:Json(name = "double_damage_from")
        val doubleDamageFrom: List<NameResource>
    )
}