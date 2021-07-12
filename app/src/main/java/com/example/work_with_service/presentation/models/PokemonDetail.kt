package com.example.work_with_service.presentation.models


class PokemonDetail(
//    val name: String,
    val captureRate: String,
    val isBaby: Boolean,
    val habitat: String,
    val color: String,
    val abilities: List<Ability>,
    val types: List<Type>
) {

    class Ability(
        val name: String? = null,
        val effect: String
    )

    class Type(
        val name: String? = null,
        val noDamageTo: List<String> = emptyList(),
        val doubleDamageTo: List<String> = emptyList(),
        val noDamageFrom: List<String> = emptyList(),
        val doubleDamageFrom: List<String> = emptyList()
    )
}