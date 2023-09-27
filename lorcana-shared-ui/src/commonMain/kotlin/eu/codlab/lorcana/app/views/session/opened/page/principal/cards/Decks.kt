package eu.codlab.lorcana.app.views.session.opened.page.principal.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import eu.codlab.lorcana.app.theme.LocalThemeEnvironment
import eu.codlab.lorcana.app.theme.LorcanaIcons
import eu.codlab.lorcana.app.theme.MyApplicationTheme
import eu.codlab.lorcana.app.theme.lorcanaicons.Exert
import eu.codlab.lorcana.app.utils.localized
import eu.codlab.lorcana.app.views.menu.systemBackground
import eu.codlab.lorcana.app.views.widgets.StatusBarAndNavigation
import eu.codlab.lorcana.app.views.widgets.TextNormal
import eu.codlab.lorcana.resources.Resources

internal object Decks : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = Resources.strings.decks.localized()
            val icon = rememberVectorPainter(LorcanaIcons.Exert)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        StatusBarAndNavigation()

        DecksContent()
    }
}

@Composable
fun DecksContent() {
    val color = LocalThemeEnvironment.current.colors.graySemiTransparent

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBackground(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextNormal(
            text = Resources.strings.coming_soon.localized(),
            color = color
        )
    }
}

@Preview
@Composable
fun DecksContentLight() {
    MyApplicationTheme(darkTheme = false) {
        DecksContent()
    }
}

@Preview
@Composable
fun DecksContentDark() {
    MyApplicationTheme(darkTheme = true) {
        DecksContent()
    }
}
