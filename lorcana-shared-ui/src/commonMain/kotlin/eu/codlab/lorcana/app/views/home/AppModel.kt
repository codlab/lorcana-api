package eu.codlab.lorcana.app.views.home

import androidx.compose.ui.text.intl.Locale
import com.github.codlab.lorcana.lorcania.LorcanaHolder
import com.github.codlab.lorcana.lorcania.LorcanaSet
import com.github.codlab.lorcana.lorcania.LorcanaSetObject
import com.github.codlab.lorcana.shared.SharedRes
import eu.codlab.lorcana.app.utils.StateViewModel
import eu.codlab.lorcana.app.utils.launch
import eu.codlab.lorcana.app.utils.safelyReadContent
import eu.codlab.lorcana.models.FoilNormal
import eu.codlab.lorcana.models.LorcanaController
import kotlin.time.ExperimentalTime

data class AppModelState(
    var initialized: Boolean = false,
    var loading: Boolean = false,
    var loggedIn: Boolean = false,
    val lang: String = "en",
    var cardSets: List<LorcanaSet> = emptyList()
)

@OptIn(ExperimentalTime::class)
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

            val textSets = SharedRes.files.sets.safelyReadContent()
            val sets = LorcanaSetObject.fromArray(textSets)

            var cardSets: MutableList<LorcanaSet> = ArrayList()

            listOf(
                Pair(SharedRes.files.tfc, "tfc"),
                Pair(SharedRes.files.d23, "d23"),
                Pair(SharedRes.files.rotf, "rotf")
            ).forEach { fileLang ->
                val file = fileLang.first
                val lang = fileLang.second

                sets.find { it.setCode.lowercase() == lang }?.let { lorcanaSet ->
                    cardSets.add(
                        LorcanaSet(
                            LorcanaHolder.fromContent(file.safelyReadContent()),
                            lorcanaSet = lorcanaSet
                        )
                    )
                }
            }

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
