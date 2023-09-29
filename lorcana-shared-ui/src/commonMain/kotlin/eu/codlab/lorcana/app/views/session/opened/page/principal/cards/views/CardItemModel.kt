package eu.codlab.lorcana.app.views.session.opened.page.principal.cards.views

import com.github.codlab.lorcana.card.Card
import eu.codlab.lorcana.downloader.DownloadAssets

class CardItemModel(
    downloadAssets: DownloadAssets,
    lang: String,
    mode: String,
    size: String
) : AbstractCardModel<String>(
    downloadAssets,
    lang,
    mode,
    size
) {
    companion object {
        fun fake(): CardItemModel {
            return CardItemModel(DownloadAssets(), "en", "normal", "large")
        }
    }

    override fun fallback(card: Card): String? {
        return downloadAssets.remote(card, lang, mode, size)
    }

    /*
    fun load(card: Card) = launch {
        listOf(
            Pair("large", "normal"),
            Pair("small", "normal")
        ).forEach { pair ->
            val size = pair.first
            val mode = pair.second

            try {
                val lang = Locale.current.language.split("_")[0].lowercase()

                val exists = downloadAssets.exists(
                    card = card,
                    lang = lang,
                    mode = mode, size = size
                )
                println("launched effect ${card.cardNumber} $exists")

                if (!exists) {
                    println("loading ?")
                    downloadAssets.fetch(
                        card = card,
                        lang = lang,
                        mode = mode,
                        size = size
                    )
                } else {
                    println("nothing")
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }
    */
}
