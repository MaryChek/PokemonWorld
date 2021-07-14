package com.example.work_with_service.domain.models

import android.util.Log


open class Resource<out T>(val data: T? = null, val status: Status, val message: String? = null) {

    val isFinishedSuccessfully: Boolean =
        data != null && status == Status.SUCCESS

    val loading: Boolean =
        status == Status.LOADING

    val error: Boolean =
        status == Status.ERROR

    init {
        if (!isFinishedSuccessfully) {
            Log.e("Resource_error", message ?: UNKNOWN_ERROR)
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
    }

    companion object {
        private const val UNKNOWN_ERROR = "Unknown error"

        fun <T> success(data: T): Resource<T> =
            Resource(data = data, status = Status.SUCCESS)

        fun <T> error(message: String): Resource<T> =
            Resource(message = message, status = Status.ERROR)

        fun <T> loading() : Resource<T> =
            Resource(status = Status.LOADING)


        fun <T> Resource<T>?.isInLoadingState(): Boolean =
            this != null && this.loading

        fun <T> Resource<T>?.isFinishedSuccessfully(): Boolean =
            this != null && this.isFinishedSuccessfully

        fun <T> Resource<T>?.isInErrorState(): Boolean =
            this != null && this.error
    }
}