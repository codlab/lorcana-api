package com.github.codlab.lorcana.lorcania

class LorcanaSet(
    private val lorcanaHolder: LorcanaHolder,
    private val lorcanaSet: LorcanaSetObject
) {

    private var translations: MutableMap<String, CardMap> = HashMap()
    private val cards: List<LorcanaCard>

    init {
        val languages = listOf("en", "fr", "de")

        languages.forEach {
            translations.put(it, CardMap())
        }

        cards = lorcanaHolder.props.cards.values.toList()

        cards.forEach { card ->
            languages.forEach { lang ->
                card.translation(lang)?.let { translation ->
                    translations.get(lang)?.append(card, translation)
                }
            }
        }
    }

    fun name() = lorcanaSet.name

    fun cardCount() = cards.size

    fun code() = lorcanaSet.setCode

    fun cards() = this.cards
    fun translations(language: String): CardMap? {
        return translations[language.lowercase()]
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
