package com.lorcanaapi

import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json

const val TresholdScore = 66

fun Application.configureRouting(actualApp: JvmApp) {
    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
    }
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
            }
        )
    }

    routing {
        actualApp.sets.forEach { set ->
            // for the future, it'll be better to use ?number= ?name=
            get("/${set.code().lowercase()}/list/{search}") {
                val result = set.search("en", this.call.parameters.get("search"))

                call.respond(result)
            }

            get("/${set.code().lowercase()}/list") {
                call.respond(set.cards())
            }
        }
    }
}
