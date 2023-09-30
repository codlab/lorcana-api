package eu.codlab.lorcana.app.views.session.opened.page.principal.cards

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

data class TabOptions(
    val index: Int,
    val title: String,
    val icon: Painter? = null
)

interface Tab {
    val option: TabOptions
        @Composable get

    @Composable
    fun Content()
}
