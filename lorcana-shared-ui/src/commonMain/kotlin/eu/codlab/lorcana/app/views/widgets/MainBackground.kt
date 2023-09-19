package eu.codlab.lorcana.app.views.widgets

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import eu.codlab.lorcana.app.theme.LocalDarkTheme

fun Modifier.systemBackground(): Modifier = composed {
    this.background(
        if (LocalDarkTheme.current) {
            Color.Black
        } else {
            Color.White
        }
    )
}
