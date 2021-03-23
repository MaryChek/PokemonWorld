package com.example.work_with_service.model

import com.example.work_with_service.model.Resource.Status.*

class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR
    }

    companion object {
        fun <T: PokemonResource> success(data: T): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T: PokemonResource> error(message: String, data: T? = null): Resource<T> {
            return Resource(ERROR, data, message)
        }

//        fun <T: PokemonResource> loading(data: T? = null): Resource<T> {
//            return Resource(LOADING, data, null)
//        }
    }
}