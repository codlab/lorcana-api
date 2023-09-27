package eu.codlab.lorcana.app.views.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import eu.codlab.lorcana.app.theme.LocalDarkTheme
import eu.codlab.safearea.theme.rememberWindowInsetsController

@Composable
fun StatusBarAndNavigation() {
    val isDarkTheme = LocalDarkTheme.current
    val windowInsetsController = rememberWindowInsetsController()

    DisposableEffect(isDarkTheme) {
        // The status bars icon + content will change to a light color
        windowInsetsController?.setStatusBarContentColor(dark = !isDarkTheme)
        // The navigation bars icons will change to a light color (android only)
        windowInsetsController?.setNavigationBarsContentColor(dark = !isDarkTheme)

        onDispose {
            // nothing
        }
    }
}
