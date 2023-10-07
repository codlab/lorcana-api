package eu.codlab.lorcana.app.views.session.opened.page.principal.cards.cards.views

import com.github.codlab.lorcana.lorcania.LorcanaCard
import dev.icerock.moko.resources.ImageResource
import eu.codlab.kamel.ext.stringToKamelFile
import eu.codlab.lorcana.app.downloader.DownloadAssets
import eu.codlab.lorcana.app.utils.StateViewModel
import eu.codlab.lorcana.app.utils.getImage
import eu.codlab.lorcana.app.utils.hasLocalResource
import eu.codlab.lorcana.app.utils.launch
import io.kamel.core.utils.File
import kotlinx.coroutines.launch

data class SingleCardModelState<Fallback>(
    var loading: Boolean = false,
    var loaded: Boolean = false,
    val localFile: File? = null,
    val fallback: Fallback? = null,
    val dataForAsync: Any? = null
)

abstract class AbstractCardModel<Fallback>(
    protected val downloadAssets: DownloadAssets,
    protected val lang: String,
    protected val mode: String,
    protected val size: String
) : StateViewModel<SingleCardModelState<Fallback>>(SingleCardModelState()) {
    fun isLoading() = states.value.loading

    abstract fun fallback(card: LorcanaCard, lang: String): Fallback?

    fun imageResource(card: LorcanaCard): ImageResource {
        return card.getImage(mode, "small", lang)
    }

    fun load(card: LorcanaCard) = launch {
        updateState { copy(loading = true) }

        val usedLang = if (card.hasLocalResource(lang)) {
            lang
        } else {
            "en"
        }

        // quick draw so that we right away check if the local image exists or not
        val localFile = downloadAssets.local(card, usedLang, mode, size)
        val fallback = fallback(card, usedLang)

        if (localFile.exists()) {
            val result = stringToKamelFile(localFile.absolutePath)
            println("having image ? ${localFile.absolutePath} which exists")
            updateState {
                copy(
                    loading = false,
                    loaded = true,
                    localFile = result,
                    dataForAsync = result
                )
            }
        } else {
            updateState {
                copy(
                    loading = false,
                    loaded = true,
                    fallback = fallback,
                    dataForAsync = fallback
                )
            }
        }

        if (!card.hasLocalResource(lang)) {
            // no need to try even fetching has the card is not here anyway
            return@launch
        }

        // and now fetch outside so that we don't disrupt the experience
        launch {
            // fetching the card if required
            downloadAssets.fetch(card, usedLang)
        }
    }
}
