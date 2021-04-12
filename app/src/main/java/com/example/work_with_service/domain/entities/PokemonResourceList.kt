package com.example.work_with_service.domain.entities

class PokemonResourceList(
    val count: Int,
    val next: String,
    val results: List<NameApiResource>
)