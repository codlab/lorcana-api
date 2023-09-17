import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import eu.codlab.lorcana.app.views.home.App
import org.jetbrains.skiko.wasm.onWasmReady

@JsExport
fun main() {
    console.log("loaded")

    onWasmReady {
        BrowserViewportWindow("Chat") {
            Column(modifier = Modifier.fillMaxSize()) {
                App(isDarkTheme = true)
            }
        }
    }
}
