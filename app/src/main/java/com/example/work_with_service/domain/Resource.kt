package com.example.work_with_service.domain

import com.example.work_with_service.domain.Status.ERROR
import com.example.work_with_service.domain.Status.SUCCESS
import com.example.work_with_service.domain.Status.LOADING

class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(SUCCESS, data, null)

        fun <T> error(message: String, data: T? = null): Resource<T> =
            Resource(ERROR, data, message)

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}