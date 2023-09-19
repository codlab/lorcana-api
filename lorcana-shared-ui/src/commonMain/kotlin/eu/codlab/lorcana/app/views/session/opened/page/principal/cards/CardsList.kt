package eu.codlab.lorcana.app.views.session.opened.page.principal.cards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.github.codlab.lorcana.sharedui.Resources
import dev.icerock.moko.resources.compose.painterResource
import eu.codlab.lorcana.app.theme.LorcanaIcons
import eu.codlab.lorcana.app.theme.lorcanaicons.Inkpot
import eu.codlab.lorcana.app.views.home.GlobalApp
import eu.codlab.lorcana.app.views.session.opened.page.principal.cards.views.CardItem
import eu.codlab.lorcana.app.views.widgets.StatusBarAndNavigation

internal class CardsList : Tab {

    private val minGridCellSize = 128.dp
    private val gridCellPadding = 10.dp
    private val ratio = 0.75f

    override val options: TabOptions
        @Composable
        get() {
            val title = "Cards"
            val icon = rememberVectorPainter(LorcanaIcons.Inkpot)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        StatusBarAndNavigation()

        val states by GlobalApp.states.collectAsState()

        val default = painterResource(Resources.images.cardBackx300)

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = minGridCellSize)
        ) {
            items(states.cards.size) { photo ->
                Box(
                    modifier = Modifier
                        .aspectRatio(ratio)
                        .fillMaxWidth()
                        .padding(gridCellPadding)
                ) {
                    CardItem(states.cards[photo], default)
                }
            }
        }
    }
}
