package com.lorcanaapi

import com.github.codlab.lorcana.card.Card
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.util.pipeline.PipelineContext
import kotlinx.serialization.json.Json
import me.xdrop.fuzzywuzzy.FuzzySearch

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
        // for the future, it'll be better to use ?number= ?name=
        get("/tfc/list/{search}") {
            this.searchUsingFuzzy(actualApp, this.call.parameters.get("search"))
        }

        get("/tfc/list") {
            call.respond(actualApp.cards)
        }
    }
}

@Suppress("ReturnCount")
suspend fun PipelineContext<Unit, ApplicationCall>.searchUsingFuzzy(
    actualApp: JvmApp,
    search: String?
) {
    val emptyArray: Array<Card> = emptyArray()

    if (null == search) {
        call.respond(emptyArray)
        return
    }

    // if we have a number
    search.toIntOrNull()?.let { number ->
        actualApp.cards.find { it.cardNumber == number }?.let { card ->
            return call.respond(arrayOf(card))
        }

        return call.respond(emptyArray)
    }

    // fallback to name
    actualApp.cards.filter { it.name.lowercase() == search.lowercase() }.let {
        if (it.isNotEmpty()) {
            return call.respond(it)
        }
    }

    // and then a regular search
    val result = FuzzySearch.extractAll(
        search,
        actualApp.map.keys
    )

    call.respond(
        result.filter { it.score > TresholdScore }.map { actualApp.map[it.string] }.filterNotNull()
    )
}
