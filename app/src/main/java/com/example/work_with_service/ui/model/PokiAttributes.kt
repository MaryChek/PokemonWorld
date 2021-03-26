package com.example.work_with_service.ui.model

sealed class PokiAttributes

class ListPokemonAttributes(
    val listAttributes: List<PokemonAttributes>
) : PokiAttributes()

class PokemonAttributes(
    val imageUrl: String,
    val name: String
)

class PokemonInfo(
    val imageUrl: String,
    val base: Base,
    val abilities: List<PokiAbility>,
    val types: List<PokiType>
) : PokiAttributes()

class Base(
    val name: String = "",
    val baseExperience: Int = 0,
    val captureRate: Int? = 0,
    val height: Int = 0,
    val weight: Int = 0,
    val isBaby: Boolean = false,
    val habitat: String = "",
    val color: String = ""
)

class PokiAbility(
    val name: String? = null,
    val effect: String
)

class PokiType(
    val name: String? = null,
    val noDamageTo: List<String> = emptyList(),
    val doubleDamageTo: List<String> = emptyList(),
    val noDamageFrom: List<String> = emptyList(),
    val doubleDamageFrom: List<String> = emptyList()
)