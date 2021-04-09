package com.example.work_with_service.domain.entities

import com.squareup.moshi.Json

class Type (
    val name: String,
    @field:Json(name = "damage_relations")
    val damageRelations: TypeRelations
) {

    class TypeRelations(
        @field:Json(name = "no_damage_to")
        val noDamageTo: List<NameApiResource>,
        @field:Json(name = "double_damage_to")
        val doubleDamageTo: List<NameApiResource>,
        @field:Json(name = "no_damage_from")
        val noDamageFrom: List<NameApiResource>,
        @field:Json(name = "double_damage_from")
        val doubleDamageFrom: List<NameApiResource>
    )
}