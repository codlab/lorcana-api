package eu.codlab.lorcana.app.views.session.opened.page.principal.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import eu.codlab.lorcana.app.theme.LorcanaIcons
import eu.codlab.lorcana.app.theme.lorcanaicons.Pip
import eu.codlab.lorcana.app.views.widgets.StatusBarAndNavigation

internal object LoreCounter : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Lore"
            val icon = rememberVectorPainter(LorcanaIcons.Pip)

            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        StatusBarAndNavigation()

        Column(
            Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            Arrangement.spacedBy(5.dp)
        ) {
        }
    }
}
