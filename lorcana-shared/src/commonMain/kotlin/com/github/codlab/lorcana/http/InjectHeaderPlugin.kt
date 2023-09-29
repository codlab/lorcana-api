package com.github.codlab.lorcana.http

import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.request.HttpRequestBuilder

class InjectHeaderPluginConfig {
    var onRequest: ((request: HttpRequestBuilder) -> Unit)? = null
}

internal val InjectHeaderPlugin = createClientPlugin(
    "InjectHeaderPlugin",
    ::InjectHeaderPluginConfig
) {
    val block = pluginConfig.onRequest
    onRequest { request, _ ->
        if (null != block) {
            block(request)
        }
    }
}
