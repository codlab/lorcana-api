package com.lorcanaapi

import com.github.codlab.lorcana.card.Card
import com.github.codlab.lorcana.files.readContent
import com.github.codlab.lorcana.resources.SharedRes
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.coroutines.runBlocking

var cards: List<Card> = emptyList()

fun main() {
    cards = runBlocking {
        val textCards = SharedRes.files.allCards.readContent()
        return@runBlocking Card.fromArray(textCards)
    }

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting(JvmApp(cards))
}
