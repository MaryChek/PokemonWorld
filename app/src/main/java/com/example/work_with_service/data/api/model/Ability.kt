package com.example.work_with_service.data.api.model

import com.squareup.moshi.Json

class Ability(
    val name: String,
    @field:Json(name = "effect_entries")
    val effectEntries: List<VerboseEffect>
) {
    class VerboseEffect(
        val effect: String,
        val language: NameApiResource
    )
}
