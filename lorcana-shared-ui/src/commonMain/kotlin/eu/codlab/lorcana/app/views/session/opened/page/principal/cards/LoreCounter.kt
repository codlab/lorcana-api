package eu.codlab.lorcana.app.views.session.opened.page.principal.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import eu.codlab.lorcana.app.theme.LocalDarkTheme
import eu.codlab.lorcana.app.theme.LorcanaIcons
import eu.codlab.lorcana.app.theme.MyApplicationTheme
import eu.codlab.lorcana.app.theme.lorcanaicons.Pip
import eu.codlab.lorcana.app.views.menu.systemBackground
import eu.codlab.lorcana.app.views.widgets.StatusBarAndNavigation
import eu.codlab.lorcana.app.views.widgets.TextNormal

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

        LoreCounterContent()
    }
}

@Composable
fun LoreCounterContent() {
    val color = if (LocalDarkTheme.current) {
        Color(0x66ffffff)
    } else {
        Color(0x66000000)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBackground(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextNormal(
            text = "Coming soon",
            color = color
        )
    }
}

@Preview
@Composable
fun LoreCounterContentLight() {
    MyApplicationTheme(darkTheme = false) {
        DecksContent()
    }
}

@Preview
@Composable
fun LoreCounterContentDark() {
    MyApplicationTheme(darkTheme = true) {
        DecksContent()
    }
}
