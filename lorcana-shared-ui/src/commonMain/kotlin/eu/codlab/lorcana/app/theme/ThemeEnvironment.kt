package eu.codlab.lorcana.app.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val fullBlack = Color(0xff000000)
private val unselected = Color(0xff7a6b63)
private val selected = Color(0xffeec788)

private val colorNavigationsDark = ColorBottomNavigations(
    background = fullBlack,
    unselected = unselected,
    selected = selected
)

data class ThemeEnvironment(
    val defaultPadding: Dp = 16.dp,
    val navigationColors: ColorBottomNavigations = colorNavigationsDark,
    val colors: ColorTheme = ColorTheme(
        graySemiTransparent = AppColor.GraySemiTransparentDark
    ),
    val gradientStart: Color = AppColor.Primary,
    val gradientEnd: Color = AppColor.Blue
)

fun createEnvironmentDark(environment: ThemeEnvironment) = environment.copy(
    navigationColors = colorNavigationsDark,
    colors = ColorTheme(
        graySemiTransparent = AppColor.GraySemiTransparentDark
    ),
    gradientStart = AppColor.Primary,
    gradientEnd = AppColor.Blue
)

fun createEnvironmentLight(environment: ThemeEnvironment) = environment.copy(
    navigationColors = ColorBottomNavigations(
        background = fullBlack,
        unselected = unselected,
        selected = selected
    ),
    colors = ColorTheme(
        graySemiTransparent = AppColor.GraySemiTransparentDark
    ),
    gradientStart = Color.White,
    gradientEnd = AppColor.BlueLighter
)
