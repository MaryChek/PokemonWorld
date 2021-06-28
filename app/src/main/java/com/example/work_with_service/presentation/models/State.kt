package com.example.work_with_service.presentation.models

import java.io.Serializable

class State (
    var status: Status,
    val errorMassage: String? = null
) : Serializable {

    fun isInLoadingState(): Boolean =
        status == Status.LOADING

    fun isFinishedSuccessfully(): Boolean =
        status == Status.SUCCESS

    fun isInErrorState(): Boolean =
        status == Status.ERROR

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun success(): State =
            State(Status.SUCCESS)

        fun error(errorMassage: String?): State =
            State(Status.ERROR, errorMassage)

        fun loading(): State =
            State(Status.LOADING)
    }
}