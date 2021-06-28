package com.example.work_with_service.presentation.mappers

import com.example.work_with_service.domain.models.Status
import com.example.work_with_service.presentation.models.State
import java.util.Locale

open class BasePokemonMapper {
    fun mapToState(status: Status, errorMessage: String?): State =
        when (status) {
            Status.SUCCESS -> State.success()
            Status.ERROR -> State.error(errorMessage)
        }

    protected fun firstUpperCase(word: String): String =
        word.substring(0, 1).toUpperCase(Locale.ROOT) + word.substring(1)

    protected fun firstLowerCase(word: String): String =
        word.substring(0,1).toLowerCase(Locale.ROOT) + word.substring(1)
}