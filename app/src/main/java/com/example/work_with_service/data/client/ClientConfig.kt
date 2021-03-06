package com.example.work_with_service.data.client

import android.annotation.SuppressLint
import okhttp3.OkHttpClient
import java.lang.Exception
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class ClientConfig(
    val baseUrl: String = BASE_URL,
    val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
) {
    init {
        setupAcceptAllCertificates()
    }

    private fun setupAcceptAllCertificates() {
        try {
            val trustManager = object : X509TrustManager {
                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?, authType: String?
                ) = Unit

                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) = Unit

                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun getAcceptedIssuers(): Array<X509Certificate?> =
                    arrayOfNulls(0)
            }
            val trustManagers: Array<TrustManager> = arrayOf(trustManager)

            val sslContext: SSLContext = SSLContext.getInstance(PROTOCOL_TLS)
            sslContext.init(null, trustManagers, SecureRandom())

            okHttpClientBuilder.sslSocketFactory(sslContext.socketFactory, trustManager)
            okHttpClientBuilder.hostnameVerifier { _, _ ->
                true
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    companion object {
        private const val PROTOCOL_TLS = "SSL"
        private const val BASE_URL = "https://pokeapi.co/api/v2/"
    }
}