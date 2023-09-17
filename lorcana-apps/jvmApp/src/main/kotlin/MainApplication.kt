import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import eu.codlab.lorcana.app.views.home.App

fun main() = singleWindowApplication(
    title = "Lorcana",
    state = WindowState(size = DpSize(500.dp, 800.dp))
) {
    App(
        //windowSize = WindowSize.basedOnWidth(windowState.size.width),
        isDarkTheme = false
    ) // isSystemInDarkTheme())
}
