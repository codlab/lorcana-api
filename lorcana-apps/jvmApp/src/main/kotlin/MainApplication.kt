import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import eu.codlab.lorcana.app.views.home.App
import eu.codlab.lorcana.app.views.widgets.ProvideSafeArea
import moe.tlaster.precompose.PreComposeWindow

fun main() = application {
    PreComposeWindow(
        onCloseRequest = {},
        title = "Lorcana",
        state = WindowState(size = DpSize(700.dp, 500.dp))
    ) {
        ProvideSafeArea { // this will need to be moved inside the ui project
            App(
                isDarkTheme = false
            )
        }
    }
}
