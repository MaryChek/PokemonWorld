package com.example.work_with_service.domain.models

class Pokemon(
    val name: String,
    val baseExperience: Int,
    val height: Int,
    val weight: Int,
    val abilityNames: List<String>,
    val typeNames: List<String>,
    val imageUrl: String
)