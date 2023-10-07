package eu.codlab.lorcana.app.views.home

import androidx.compose.ui.text.intl.Locale
import com.github.codlab.lorcana.card.LorcanaSet
import com.github.codlab.lorcana.card.SetsLoader
import eu.codlab.lorcana.app.utils.StateViewModel
import eu.codlab.lorcana.app.utils.launch
import eu.codlab.lorcana.models.FoilNormal
import eu.codlab.lorcana.models.LorcanaController

data class AppModelState(
    var initialized: Boolean = false,
    var loading: Boolean = false,
    var loggedIn: Boolean = false,
    val lang: String = "en",
    var cardSets: List<LorcanaSet> = emptyList()
)

class AppModel : StateViewModel<AppModelState>(AppModelState()) {

    private val databaseController = LorcanaController()

    companion object {
        fun fake(): AppModel {
            Locale.current.language.split("_")[0].lowercase()
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
        @Suppress("TooGenericExceptionCaught")
        try {
            databaseController.selectAll()

            val cardSets = SetsLoader().load()

            updateState {
                copy(
                    initialized = true,
                    cardSets = cardSets,
                    lang = Locale.current.language.split("_")[0].lowercase()
                )
            }
        } catch (e: Throwable) {
            println("having exception $e")
            e.printStackTrace()
        }
    }
}
