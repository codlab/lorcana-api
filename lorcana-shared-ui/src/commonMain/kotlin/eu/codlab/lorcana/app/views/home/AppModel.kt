package eu.codlab.lorcana.app.views.home

import com.github.codlab.lorcana.card.Card
import com.github.codlab.lorcana.files.readContent
import com.github.codlab.lorcana.resources.SharedRes
import eu.codlab.lorcana.app.utils.StateViewModel
import eu.codlab.lorcana.app.utils.launch
import kotlin.time.ExperimentalTime

data class AppModelState(
    var cards: List<Card> = emptyList(),
    var initialized: Boolean = false,
    var loading: Boolean = false,
    var loggedIn: Boolean = false
)

@OptIn(ExperimentalTime::class)
class AppModel : StateViewModel<AppModelState>(AppModelState()) {

    companion object {
        fun fake(): AppModel {
            return AppModel()
        }
    }

    fun isInitialized() = states.value.initialized

    fun initialize() = launch {
        val textCards = SharedRes.files.allCards.readContent()
        val cards = Card.fromArray(textCards)

        updateState {
            copy(
                initialized = true,
                cards = cards
            )
        }
    }
}
