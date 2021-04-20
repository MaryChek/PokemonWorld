package com.example.work_with_service.ui.model.pokemondetail

import androidx.annotation.StringRes

class PokemonDetail(
    val imageUrl: String?,
    val name: String,
    val baseExperience: Int,
    val captureRate: Int,
    val height: Int,
    val weight: Double,
    @StringRes
    val resIdAge: Int,
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