package com.example.work_with_service.data.service

import com.example.work_with_service.data.entities.PokemonResource
import com.example.work_with_service.data.service.Status.*

class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {
        fun <T : PokemonResource> success(data: T): Resource<T> =
            Resource(SUCCESS, data, null)

        fun <T : PokemonResource> error(message: String, data: T? = null): Resource<T> =
            Resource(ERROR, data, message)
    }
}