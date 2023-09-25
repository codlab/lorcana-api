package com.github.codlab.lorcana.lorcania

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
data class LorcaniaHolder(
    val component: String,
    val props: LorcaniaHolderProps
) {
    companion object {
        private val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        fun fromContent(content: String): LorcaniaHolder {
            return json.decodeFromString(content)
        }
    }
}

@Serializable
data class LorcaniaHolderProps(
    val cards: Map<String, LorcaniaCard>
)

@Serializable
data class LorcaniaCard(
    val id: Int = 0,
    val name: String = "",
    val title: String = "",
    val cost: Int = 0,
    val inkwell: Int = 0,
    val attack: Int = 0,
    val defence: Int = 0,
    val color: Int = 0,
    val type: String = "",
    val action: String = "",
    val flavour: String = "",
    val separator: String = "",
    val stars: Int = 0,
    val illustrator: String = "",
    @SerialName("card_set_id")
    val cardSetId: Int = 1,
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
    private val languages: Map<String, LorcaniaCardTranslation> = emptyMap(),
    val edition: List<LorcaniaCardEdition> = emptyList()
) {
    fun image(): String {
        return image.replace("\\/", "/")
    }

    fun getSetId(): String {
        return listOf("d23", "tfc", "rtf")[cardSetId - 1]
    }

    fun translation(language: String): LorcaniaCardTranslation? {
        return languages[language.uppercase()]
    }

    companion object {
        fun fake(): LorcaniaCard {
            return LorcaniaCard(
                cardSetId = 1
            )
        }
    }
}

@Serializable
data class LorcaniaCardTranslation(
    @SerialName("card_id")
    val cardId: Int,
    val language: String = "",
    val name: String = "",
    val title: String = "",
    val action: String = "",
    val flavour: String = "",
    private val image: String = ""
) {
    fun image(): String {
        return image.replace("\\/", "/")
    }
}

@Serializable
data class LorcaniaCardEdition(
    val id: Int,
    val name: String, // Regular or foil
    val code: String // regular or foil
)
