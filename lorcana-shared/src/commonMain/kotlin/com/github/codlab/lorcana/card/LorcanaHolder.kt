package com.github.codlab.lorcana.card

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class LorcanaHolder(
    val cards: List<LorcanaCard>
) {
    companion object {
        private val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        fun fromContent(content: String): LorcanaHolder {
            return LorcanaHolder(json.decodeFromString(content))
        }
    }
}

@Serializable
data class LorcanaCard(
    val id: Int = 0,
    val name: String = "",
    val title: String = "",
    val cost: Int = 0,
    val inkwell: Int = 0,
    val attack: Int = 0,
    val defence: Int = 0,
    val color: Int = 0,
    val type: String = "",
    val actions: List<Action> = emptyList(),
    val flavour: String = "",
    val separator: String = "",
    val stars: Int = 0,
    val illustrator: String = "",
    val setCode: String = "d23",
    val language: String = "",
    val number: Int = 12,
    val pack: String = "204", // 204?
    val rarity: String = "",
    private val image: String = "",
    @SerialName("blurhash")
    val blurHash: String = "",
    @SerialName("franchise_id")
    val franchiseId: Int = 0,
    val final: Int = 1,
    val spoiler: Int = 1,
    @SerialName("created_at")
    val createdAt: String = "",
    @SerialName("updated_at")
    val updatedAt: String = "",
    private val languages: Map<String, LorcanaCardTranslation> = emptyMap(),
    val edition: List<LorcanaCardEdition> = emptyList()
) {
    fun image(): String {
        return image.replace("\\/", "/")
    }

    fun translation(language: String): LorcanaCardTranslation? {
        return languages[language.lowercase()]
    }

    fun getRemoteUrl(mode: String, size: String, lang: String = "en"): String {
        val root = "https://lorcana.codlab.eu/images/"
        return "$root/${setCode}_${mode}_${size}_${number}_$lang@1x.webp".lowercase()
    }

    fun getLocalUrl(mode: String, size: String, lang: String = "en"): String {
        return "${setCode}_${mode}_${size}_${number}_$lang.jpg".lowercase()
    }

    companion object {
        fun fake(): LorcanaCard {
            return LorcanaCard()
        }
    }
}

@Serializable
data class Action(
    val name: String? = null,
    val description: List<SubDescription>
) {
    @Serializable
    data class SubDescription(
        /**
         * text or note
         */
        val type: String,
        val text: String
    )
}

@Serializable
data class LorcanaCardTranslation(
    val name: String = "",
    val title: String = "",
    val actions: List<Action> = emptyList(),
    val flavour: String = "",
    private val image: String = ""
) {
    fun image(): String {
        return image.replace("\\/", "/")
    }
}

@Serializable
data class LorcanaCardEdition(
    val id: Int,
    val name: String, // Regular or foil
    val code: String // regular or foil
)
