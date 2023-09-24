package eu.codlab.lorcana.app.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ThemeEnvironment(
    val defaultPadding: Dp = 16.dp,
    val navigationColors: ColorBottomNavigations,
    val colors: ColorTheme
)
