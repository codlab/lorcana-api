package com.github.codlab.lorcana.card

import com.github.codlab.lorcana.files.readContent
import com.github.codlab.lorcana.resources.SharedRes
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CardTest {

    @Test
    fun test() = runTest {
        val textCards = SharedRes.files.allCards.readContent()
        val cards = Card.fromArray(textCards)

        assertEquals(204, cards.size)
        println(cards)
    }
}
