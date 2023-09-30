package eu.codlab.lorcana.app.views.session.opened.page.licenses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import eu.codlab.lorcana.app.views.widgets.StatusBarAndNavigation

@Composable
fun LicensesScreen() {
    StatusBarAndNavigation()

    Column(
        Modifier.fillMaxSize()
            .background(Color.Transparent),
        Arrangement.spacedBy(5.dp)
    ) {
        LicensesContent()
    }
}
