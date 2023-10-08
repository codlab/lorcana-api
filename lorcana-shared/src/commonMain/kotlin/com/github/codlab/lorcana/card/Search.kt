package com.github.codlab.lorcana.card

import com.willowtreeapps.fuzzywuzzy.diffutils.FuzzySearch

const val ThresholdScore = 66

class Search {
    fun match(
        language: String,
        query: String?,
        cards: List<LorcanaCard>,
        cardMap: CardMap
    ): List<LorcanaCard> {
        if (query.isNullOrEmpty()) return emptyList()

        val found = mutableListOf<LorcanaCard>()

        found += cards.filter {
            val translation = it.translation(language) ?: return@filter false

            "${translation.name.lowercase()} ${translation.title.lowercase()}"
                .lowercase().contains(query.lowercase())
        }

        val result = FuzzySearch.extractAll(
            query.lowercase(),
            cardMap.keys()
        )

        val treshold = result.filter { it.score > ThresholdScore }

        return found + treshold.map { cardMap.get(it.string ?: "") }.filter { card ->
            null == found.find { null != card && it.number == card.number }
        }.filterNotNull()
    }
}
