package eu.codlab.lorcana.app.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import com.github.codlab.lorcana.resources.SharedRes
import dev.icerock.moko.resources.compose.fontFamilyResource

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
        return fontFamilyResource(fontResource = SharedRes.fonts.Lorcana.regular)
    }
}
