package com.github.codlab.lorcana.card

class LorcanaSet(
    private val lorcanaHolder: LorcanaHolder,
    private val lorcanaSet: LorcanaSetObject
) {

    private var translations: MutableMap<String, CardMap> = HashMap()

    private val search = Search()

    init {
        val languages = listOf("en", "fr", "de")

        languages.forEach {
            translations.put(it, CardMap())
        }

        lorcanaHolder.cards.forEach { card ->
            languages.forEach { lang ->
                card.translation(lang)?.let { translation ->
                    translations.get(lang)?.append(card, translation)
                }
            }
        }
    }

    fun name() = lorcanaSet.name

    fun cardCount() = lorcanaHolder.cards.size

    fun code() = lorcanaSet.setCode

    fun cards() = lorcanaHolder.cards

    fun translations(language: String): CardMap? {
        return translations[language.lowercase()]
    }

    fun search(language: String, query: String?): List<LorcanaCard> {
        val holder = translations(language) ?: return emptyList()

        return search.match(language, query, cards(), holder)
    }
}

class CardMap {
    private val cardMaps: MutableMap<String, LorcanaCard> = HashMap()

    fun append(card: LorcanaCard, translation: LorcanaCardTranslation) {
        cardMaps["${translation.name} ${translation.title}"] = card
    }

    fun keys() = cardMaps.keys

    fun get(key: String) = cardMaps[key]
}
