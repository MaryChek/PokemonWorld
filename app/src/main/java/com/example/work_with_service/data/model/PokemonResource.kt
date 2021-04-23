package com.example.work_with_service.data.model

import com.squareup.moshi.Json

class PokemonResourceList(
    val pokemonNames: List<String>
)

class Pokemon(
    val name: String,
    val baseExperience: Int,
    val height: Int,
    val weight: Int,
    val abilityNames: List<String>,
    val typeNames: List<String>,
    val frontDefault: String
)

class PokemonSpecies(
    val isBaby: Boolean,
    val habitat: String,
    val color: String,
    val captureRate: Int
)

class Ability(
    val name: String,
    val effects: List<Effect>
) {

    class Effect(
        val description: String,
        val language: String
    )
}

class Type(
    val name: String,
    val damageTypes: DamageTypes
) {

    class DamageTypes(
        val noDamageTo: List<String>,
        val doubleDamageTo: List<String>,
        val noDamageFrom: List<String>,
        val doubleDamageFrom: List<String>
    )
}