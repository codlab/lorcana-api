import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import eu.codlab.lorcana.app.views.home.App
import eu.codlab.safearea.views.ProvideSafeArea
import moe.tlaster.precompose.preComposeWindow
import org.jetbrains.skiko.wasm.onWasmReady

@JsExport
fun main() {
    onWasmReady {
        preComposeWindow {
            ProvideSafeArea {
                Column(modifier = Modifier.fillMaxSize()) {
                    App(isDarkTheme = true)
                }
            }
        }
    }
}
