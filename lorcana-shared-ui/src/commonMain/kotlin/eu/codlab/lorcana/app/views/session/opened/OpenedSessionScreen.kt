package eu.codlab.lorcana.app.views.session.opened

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import eu.codlab.lorcana.app.views.widgets.SafeArea
import eu.codlab.lorcana.app.views.widgets.StatusBarAndNavigation

internal class OpenedSessionScreen : Screen {
    @Composable
    override fun Content() {
        StatusBarAndNavigation()

        SafeArea {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.Transparent),
                Arrangement.spacedBy(5.dp)
            ) {
                OpenedSessionContent()
            }
        }
    }
}
