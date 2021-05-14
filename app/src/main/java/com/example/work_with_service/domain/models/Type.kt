package com.example.work_with_service.domain.models

class Type(
    val name: String,
    val damageTypes: DamageTypes
) {

    class DamageTypes(
        val noDamageTo: List<String>,
        val doubleDamageTo: List<String>,
        val noDamageFrom: List<String>,
        val doubleDamageFrom: List<String>
    )
}