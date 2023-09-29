package com.github.codlab.lorcana.http

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder

expect fun createClient(
    onRequest: ((request: HttpRequestBuilder) -> Unit)? = null
): HttpClient
