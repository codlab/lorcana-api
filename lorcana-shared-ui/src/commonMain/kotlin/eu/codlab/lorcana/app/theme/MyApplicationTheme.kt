package eu.codlab.lorcana.app.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val navigationLight = ColorBottomNavigations(
    background = Color(0xff000000),
    unselected = Color(0xff7a6b63),
    selected = Color(0xffeec788)
)

val navigationDark = ColorBottomNavigations(
    background = Color(0xff000000),
    unselected = Color(0xff7a6b63),
    selected = Color(0xffeec788)
)

val LocalDarkTheme = compositionLocalOf { false }
val LocalNavigationColors = compositionLocalOf { navigationDark }

@Suppress("MagicNumber")
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val currentTheme = LocalDarkTheme.current
    val currentNavigationColors = LocalNavigationColors.current

    var isDarkTheme by remember { mutableStateOf(currentTheme) }
    var selectedNavigationColors by remember { mutableStateOf(currentNavigationColors) }

    DisposableEffect(darkTheme) {
        println("new status for $darkTheme")
        isDarkTheme = darkTheme
        selectedNavigationColors = if (isDarkTheme) navigationDark else navigationLight

        onDispose {
            // nothing
        }
    }

    val colors = if (darkTheme) {
        darkColors(
            primary = Color(0xFFBB86FC),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC5)
        )
    } else {
        lightColors(
            primary = Color(0xFF6200EE),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC5)
        )
    }
    val typography = Typography(
        body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    CompositionLocalProvider(
        LocalDarkTheme provides isDarkTheme,
        LocalNavigationColors provides currentNavigationColors
    ) {
        MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = shapes
        ) {
            content()
        }
    }
}
