package eu.codlab.lorcana.app.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import dev.icerock.moko.resources.compose.asFont
import eu.codlab.lorcana.resources.Resources

object FontIcon {
    const val menu = "m"
    const val bug = "b"
    const val linkedin = "l"
    const val slack = "s"
    const val github = "g"
    const val web = "n"
    const val close = "c"
    const val back = "b"

    @Composable
    fun fontFamily(): FontFamily {
        return FontFamily(
            Resources.fonts.Lorcana.regular.asFont(
                weight = FontWeight.Normal,
                style = FontStyle.Normal
            ) ?: error("Invalid font")
        )
    }
}
