package com.nagyrobi144.dogify.api

import com.nagyrobi144.dogify.BuildKonfig
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

internal open class KtorApi {

    private val jsonConfiguration = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(jsonConfiguration)
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }

    /**
     * Use this method for configuring the request url
     */
    fun HttpRequestBuilder.apiUrl(path: String) {
        url {
            takeFrom(BuildKonfig.baseUrl)
            path(API_PATH, path)
        }
    }

    companion object {
        private const val API_PATH = "api"
    }
}