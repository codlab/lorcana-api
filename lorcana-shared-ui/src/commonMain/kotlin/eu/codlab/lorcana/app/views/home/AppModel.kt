package eu.codlab.lorcana.app.views.home

import com.github.codlab.lorcana.card.Card
import com.github.codlab.lorcana.files.readContent
import com.github.codlab.lorcana.resources.SharedRes
import eu.codlab.lorcana.app.utils.StateViewModel
import eu.codlab.lorcana.app.utils.launch
import eu.codlab.lorcana.models.FoilNormal
import eu.codlab.lorcana.models.LorcanaController
import kotlin.time.ExperimentalTime

data class AppModelState(
    var cards: List<Card> = emptyList(),
    var cardMaps: Map<String, Card> = emptyMap(),
    var initialized: Boolean = false,
    var loading: Boolean = false,
    var loggedIn: Boolean = false
)

@OptIn(ExperimentalTime::class)
class AppModel : StateViewModel<AppModelState>(AppModelState()) {

    private val databaseController = LorcanaController()

    companion object {
        fun fake(): AppModel {
            return AppModel()
        }
    }

    fun getCardNumbers(setCode: String, cardNumber: Long): FoilNormal {
        return databaseController.get(setCode, cardNumber)
    }

    fun save(setCode: String, cardNumber: Long, numbers: FoilNormal) {
        databaseController.save(setCode, cardNumber, numbers)
    }

    fun isInitialized() = states.value.initialized

    fun initialize() = launch {

        databaseController.selectAll()

        val textCards = SharedRes.files.allCards.readContent()
        val cards = Card.fromArray(textCards).sortedBy {
            it.cardNumber
        }

        val cardMaps: MutableMap<String, Card> = HashMap()

        cards.forEach {
            cardMaps["${it.name} ${it.subTitle}"] = it
        }

        updateState {
            copy(
                initialized = true,
                cards = cards,
                cardMaps = cardMaps
            )
        }
    }
}
