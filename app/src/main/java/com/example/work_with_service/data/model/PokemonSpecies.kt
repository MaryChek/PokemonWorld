package com.example.work_with_service.data.model

import com.squareup.moshi.Json

class PokemonSpecies(
    val name: String,
    @field:Json(name = "is_Baby")
    val isBaby: Boolean,
    val habitat: NameApiResource,
    val color: NameApiResource,
    @field:Json(name = "capture_rate")
    val captureRate: Int
)