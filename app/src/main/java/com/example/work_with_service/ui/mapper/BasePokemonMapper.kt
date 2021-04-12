package com.example.work_with_service.ui.mapper

import java.util.Locale

open class BasePokemonMapper {
    fun firstUpperCase(word: String): String =
        word.substring(0, 1).toUpperCase(Locale.ROOT) + word.substring(1)
}