package com.github.codlab.lorcana.http

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@Suppress("MagicNumber")
actual fun createClient(
    onRequest: ((request: HttpRequestBuilder) -> Unit)?
): HttpClient {
    return HttpClient(Darwin) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 12000
        }

        if (null != onRequest) {
            install(InjectHeaderPlugin) {
                this.onRequest = onRequest
            }
        }

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                }
            )
        }
    }
}
