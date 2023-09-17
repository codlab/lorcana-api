package eu.codlab.lorcana.app.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import com.github.codlab.lorcana.resources.SharedRes
import dev.icerock.moko.resources.compose.fontFamilyResource

object FontIcon {

    val menu = "m"
    val bug = "b"
    val linkedin = "l"
    val slack = "s"
    val github = "g"
    val web = "n"
    val close = "c"
    val back = "b"

    @Composable
    fun fontFamily(): FontFamily {
        return fontFamilyResource(fontResource = SharedRes.fonts.Lorcana.regular)
    }
}