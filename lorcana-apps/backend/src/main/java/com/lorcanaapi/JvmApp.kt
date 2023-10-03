package com.lorcanaapi

import com.github.codlab.lorcana.card.Card

class JvmApp(val cards: List<Card>) {

    val map: Map<String, Card>
    init {
        map = HashMap()
        cards.forEach { map["${it.name} ${it.subTitle}"] = it }
    }
}
