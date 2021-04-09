package com.example.work_with_service.domain.entities

import com.example.work_with_service.domain.entities.NameApiResource
import com.squareup.moshi.Json

class PokemonSpecies(
    @field:Json(name = "is_Baby")
    val isBaby: Boolean,
    val habitat: NameApiResource,
    val color: NameApiResource,
    @field:Json(name = "capture_rate")
    val captureRate: Int
)