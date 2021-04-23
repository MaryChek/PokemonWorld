package com.example.work_with_service.data.model

class PokemonResourceList(
    val count: Int,
    val next: String,
    val results: List<NameApiResource>
)