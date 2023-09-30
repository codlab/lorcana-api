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
import eu.codlab.lorcana.app.theme.LocalThemeEnvironment
import eu.codlab.lorcana.app.theme.LorcanaIcons
import eu.codlab.lorcana.app.theme.MyApplicationTheme
import eu.codlab.lorcana.app.theme.lorcanaicons.Pip
import eu.codlab.lorcana.app.views.menu.systemBackground
import eu.codlab.lorcana.app.views.widgets.StatusBarAndNavigation
import eu.codlab.lorcana.app.views.widgets.TextNormal
import eu.codlab.lorcana.resources.Resources
import eu.codlab.moko.ext.localized

internal class LoreCounter : Tab {
    override val option: TabOptions
        @Composable
        get() {
            val title = Resources.strings.lore.localized()
            val icon = rememberVectorPainter(LorcanaIcons.Pip)

            return remember {
                TabOptions(
                    index = 2,
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
