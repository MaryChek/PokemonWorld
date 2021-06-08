package com.example.work_with_service.presentation.models

import java.io.Serializable

class Status (
    var state: State,
    val errorMassage: String? = null
) : Serializable {

    fun isInLoadingState(): Boolean =
        state == State.LOADING

    fun isFinishedSuccessfully(): Boolean =
        state == State.SUCCESS

    fun isInErrorState(): Boolean =
        state == State.ERROR

    enum class State {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun success(): Status = Status(State.SUCCESS)

        fun error(errorMassage: String?): Status = Status(State.ERROR, errorMassage)

        fun loading(): Status = Status(State.LOADING)
    }
}