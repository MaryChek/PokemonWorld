package com.example.work_with_service.domain.entities

import com.example.work_with_service.domain.entities.NameApiResource

class PokemonResourceList(
    val count: Int,
    val next: String,
    val results: List<NameApiResource>
)