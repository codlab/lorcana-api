package eu.codlab.lorcana.app.views.session.opened.page.principal.cards.views

import com.github.codlab.lorcana.card.Card
import dev.icerock.moko.resources.ImageResource
import eu.codlab.lorcana.app.utils.StateViewModel
import eu.codlab.lorcana.app.utils.getImage
import eu.codlab.lorcana.app.utils.launch
import eu.codlab.lorcana.app.utils.stringToKamelFile
import eu.codlab.lorcana.downloader.DownloadAssets
import io.kamel.core.utils.File
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime

data class SingleCardModelState<Fallback>(
    var loading: Boolean = false,
    var loaded: Boolean = false,
    val localFile: File? = null,
    val fallback: Fallback? = null,
    val dataForAsync: Any? = null
)

@OptIn(ExperimentalTime::class)
abstract class AbstractCardModel<Fallback>(
    protected val downloadAssets: DownloadAssets,
    protected val lang: String,
    protected val mode: String,
    protected val size: String
) : StateViewModel<SingleCardModelState<Fallback>>(SingleCardModelState()) {
    fun isLoading() = states.value.loading

    abstract fun fallback(card: Card): Fallback?

    fun imageResource(card: Card): ImageResource {
        return card.getImage(mode, "small", "en")
    }

    fun load(card: Card) = launch {
        updateState { copy(loading = true) }

        // quick draw so that we right away check if the local image exists or not
        val localFile = downloadAssets.local(card, lang, mode, size)
        val fallback = fallback(card)

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

        // and now fetch outside so that we don't disrupt the experience
        launch {
            // fetching the card if required
            downloadAssets.fetch(card, lang)
        }
    }
}
