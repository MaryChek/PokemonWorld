package com.example.work_with_service.data.api.model

import com.squareup.moshi.Json

class Pokemon(
    val name: String,
    @field:Json(name = "base_experience")
    val baseExperience: Int,
    val height: Int,
    val weight: Int,
    val abilities: List<PokemonAbility>,
    val types: List<PokemonType>,
    val sprites: PokemonSprites
) {

    class PokemonAbility(
        val ability: NameApiResource
    )

    class PokemonType(
        val type: NameApiResource
    )

    class PokemonSprites(
        @field:Json(name = "front_default")
        val frontDefault: String
    )
}