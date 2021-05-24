package com.example.work_with_service.presentation.models

import java.io.Serializable

class Receipt(
    val status: Status
) : Serializable {
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun success(): Receipt = Receipt(Status.SUCCESS)

        fun error(): Receipt = Receipt(Status.ERROR)

        fun loading(): Receipt = Receipt(Status.LOADING)
    }
}