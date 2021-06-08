package com.example.work_with_service.domain.models

import com.example.work_with_service.domain.models.Status.ERROR
import com.example.work_with_service.domain.models.Status.SUCCESS

class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(SUCCESS, data, null)

        fun <T> error(message: String, data: T? = null): Resource<T> =
            Resource(ERROR, data, message)
    }
}