package com.github.codlab.lorcana.lorcania

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class LorcanaSetObject(
    @SerialName("set_code")
    val setCode: String = "TFC",
    @SerialName("card_count")
    val cardCount: Int = 0,
    val name: String = ""
) {
    companion object {

        fun fake(): List<LorcanaSetObject> {
            return listOf(
                LorcanaSetObject(
                    setCode = "tfc",
                    cardCount = 100,
                    name = "The First Chapter"
                ),
                LorcanaSetObject(
                    setCode = "d23",
                    cardCount = 100,
                    name = "Promo & Disney 100"
                ),
                LorcanaSetObject(
                    setCode = "rotf",
                    cardCount = 100,
                    name = "Rise Of The Floodborn"
                )
            )
        }

        private val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        fun fromArray(content: String): List<LorcanaSetObject> {
            return Json.decodeFromString(content)
        }
    }
}
