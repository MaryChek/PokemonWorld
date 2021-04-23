package com.example.work_with_service.domain.entities

class Ability(
    val name: String,
    val effects: List<Effect>
) {

    class Effect(
        val description: String,
        val language: String
    )
}