package eu.codlab.lorcana.app.views.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun TopSpacer(color: Color = Color.Transparent) {
    val height = WindowInsets.topBar.getTop(LocalDensity.current)
    val scaled = height / LocalDensity.current.density

    Spacer(
        modifier = Modifier
            .background(color = color)
            .height(scaled.dp)
    )
}
