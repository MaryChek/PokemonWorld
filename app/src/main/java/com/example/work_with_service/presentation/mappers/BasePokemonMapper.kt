package com.example.work_with_service.presentation.mappers

import java.util.Locale

open class BasePokemonMapper {
    protected fun firstUpperCase(word: String): String =
        word.substring(0, 1).toUpperCase(Locale.ROOT) + word.substring(1)

    protected fun firstLowerCase(word: String): String =
        word.substring(0,1).toLowerCase(Locale.ROOT) + word.substring(1)
}