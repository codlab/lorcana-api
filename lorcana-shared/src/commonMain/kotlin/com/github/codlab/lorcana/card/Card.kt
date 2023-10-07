package com.github.codlab.lorcana.card

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ImageUrls(
    var small: String = "",
    @SerialName("no-art")
    var noArt: String = "",
    var large: String = "",
    @SerialName("art-crop")
    var artCrop: String = "",
    var medium: String = "",
    var foil: String = ""
)

@Serializable
data class Card(
    @SerialName("lore-value")
    val loreValue: Int = 0,
    val traits: List<String> = emptyList(),
    val set: String = "",

    @SerialName("card-number")
    val cardNumber: Int = 0,
    val color: String = "",
    val strength: Int = 0,
    val artist: String = "",

    @SerialName("willpower")
    val willPower: Int = 0,
    val type: String = "",

    @SerialName("subtypes")
    val subTypes: List<String> = emptyList(),

    @SerialName("set-code")
    val setCode: String = "",
    val abilities: HashMap<String, String> = HashMap<String, String>(),

    val inkable: Boolean = false,

    val effect: String? = "",

    @SerialName("body-text")
    val bodyText: String = "",

    @SerialName("subtitle")
    val subTitle: String = "",

    @SerialName("image-urls")
    val imageUrls: ImageUrls = ImageUrls(),
    val name: String = "",

    @SerialName("ink-cost")
    val inkCost: Int = 0,

    @SerialName("flavor-text")
    val flavorText: String = "",
    val rarity: String = ""
) {
    companion object {
        fun fromContent(content: String): Card {
            return Json.decodeFromString(content)
        }

        fun fromArray(content: String): List<Card> {
            return Json.decodeFromString(content)
        }

        fun fake(): Card {
            return Card(
                setCode = "tfc",
                cardNumber = 1
            )
        }
    }

    fun getRemoteUrl(mode: String, size: String, lang: String = "en"): String {
        val root = "https://lorcana.codlab.eu/images/"
        return "$root/${this.setCode}_${mode}_${size}_${this.cardNumber}_$lang@1x.webp".lowercase()
    }

    fun getLocalUrl(mode: String, size: String, lang: String = "en"): String {
        return "${this.setCode}_${mode}_${size}_${this.cardNumber}_$lang.jpg".lowercase()
    }
}
