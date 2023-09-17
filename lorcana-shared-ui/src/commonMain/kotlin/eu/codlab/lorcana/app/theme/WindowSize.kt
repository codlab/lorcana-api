package eu.codlab.lorcana.app.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class WindowSize {
    COMPACT,
    EXPANDED;

    // Factory method that creates an instance of the class based on window width
    companion object {
        fun basedOnWidth(windowWidth: Dp): WindowSize {
            return when {
                windowWidth < 600.dp -> COMPACT
                else -> EXPANDED
            }
        }
    }
}
