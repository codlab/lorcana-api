package eu.codlab.lorcana.app

import android.os.Bundle
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.view.WindowCompat
import eu.codlab.lorcana.app.views.home.App
import moe.tlaster.precompose.lifecycle.PreComposeActivity
import moe.tlaster.precompose.lifecycle.setContent

class MainActivity : PreComposeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            App(
                isDarkTheme = isSystemInDarkTheme()
            )
        }
    }
}
