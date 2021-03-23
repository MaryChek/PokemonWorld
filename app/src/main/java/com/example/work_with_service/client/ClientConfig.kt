package com.example.work_with_service.client

import okhttp3.HttpUrl
import okhttp3.OkHttpClient

class ClientConfig(
    val rootUrl: HttpUrl = HttpUrl.parse("https://pokeapi.co/api/v2/")!!,
    val okHttpConfig: OkHttpClient.Builder = OkHttpClient.Builder()
)