package com.lorcanaapi

import com.github.codlab.lorcana.card.LorcanaSet
import com.github.codlab.lorcana.card.SetsLoader
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.coroutines.runBlocking

var sets: List<LorcanaSet> = emptyList()

fun main() {
    sets = runBlocking {
        SetsLoader().load()
    }

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting(JvmApp(sets))
    }.start(wait = true)
}
