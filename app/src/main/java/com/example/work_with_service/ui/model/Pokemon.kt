package com.example.work_with_service.ui.model

import java.io.Serializable

class Pokemon(
    val name: String,
    val baseExperience: Int,
    val height: Int,
    val weight: Double,
    val abilityNames: List<String>,
    val typeNames: List<String>,
    val frontDefault: String
) : Serializable