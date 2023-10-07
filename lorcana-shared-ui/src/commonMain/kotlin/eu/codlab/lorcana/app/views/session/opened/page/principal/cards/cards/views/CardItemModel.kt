package eu.codlab.lorcana.app.views.session.opened.page.principal.cards.cards.views

import com.github.codlab.lorcana.lorcania.LorcanaCard
import eu.codlab.lorcana.app.downloader.DownloadAssets

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

    override fun fallback(card: LorcanaCard, lang: String): String? {
        return downloadAssets.remote(card, lang, mode, size)
    }
}
