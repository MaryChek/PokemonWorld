package com.example.work_with_service.ui.model

interface PokiAttributes

class ListPokemonAttributes(
    val listAttributes: List<PokemonAttributes>
) : PokiAttributes

class PokemonAttributes(
    val imageUrl: String,
    val name: String
)

class PokemonInfo(
    val imageUrl: String?,
    val base: BaseInfo,
    val abilities: List<PokiAbility>,
    val types: List<PokiType>
) : PokiAttributes

class BaseInfo(
    val name: String,
    val baseExperience: Int,
    val captureRate: Int,
    val height: Int,
    val weight: Int,
    val isBaby: Boolean,
    val habitat: String,
    val color: String
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