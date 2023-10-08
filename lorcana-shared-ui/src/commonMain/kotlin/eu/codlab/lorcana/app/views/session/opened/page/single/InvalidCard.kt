package eu.codlab.lorcana.app.views.session.opened.page.single

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import eu.codlab.compose.theme.LocalThemeEnvironment
import eu.codlab.compose.widgets.StatusBarAndNavigation
import eu.codlab.compose.widgets.TextNormal
import eu.codlab.lorcana.app.theme.MyApplicationTheme
import eu.codlab.lorcana.app.views.menu.systemBackground
import eu.codlab.lorcana.resources.Resources
import eu.codlab.moko.ext.localized

@Composable
fun InvalidCard() {
    StatusBarAndNavigation()
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
fun InvalidCardLight() {
    MyApplicationTheme(darkTheme = false) {
        InvalidCard()
    }
}

@Preview
@Composable
fun InvalidCardDark() {
    MyApplicationTheme(darkTheme = true) {
        InvalidCard()
    }
}
