package eu.codlab.lorcana.app.views.home

import com.github.codlab.lorcana.card.Card
import com.github.codlab.lorcana.files.readContent
import com.github.codlab.lorcana.resources.SharedRes
import eu.codlab.lorcana.app.utils.StateViewModel
import eu.codlab.lorcana.app.utils.launch
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

data class AppModelState(
    var cards: List<Card> = emptyList(),
    var initialized: Boolean = false,
    var loading: Boolean = false,
    var loggedIn: Boolean = false
)

@OptIn(ExperimentalTime::class)
data class AppModel(
    val appId: String,
    val appSecret: String
) : StateViewModel<AppModelState>(AppModelState()) {

    companion object {
        fun fake(): AppModel {
            return AppModel("", "")
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

    fun logIn(email: String, password: String) = launch {
        /*val closedSession = AuthentManager.createSession(
            externalId
        ) { _: String?, _: Boolean ->
            RequestTokenFromAppIdAppSecret.run(appId, appSecret, expiresIn = 600).access_token
        }

        val openedSession = closedSession.open(
            ParticipantInfo(
                name = name
            )
        )
         */

        delay(5.seconds)

        println("logging in")

        updateState {
            copy(loading = false, loggedIn = true)
        }
    }

    fun createAccount(surname: String, lastname: String, email: String) = launch {
        delay(5.seconds)

        updateState {
            copy(loading = false, loggedIn = true)
        }
    }

    fun logOut() {
        updateState {
            copy(loading = false, loggedIn = false)
        }
    }
}
