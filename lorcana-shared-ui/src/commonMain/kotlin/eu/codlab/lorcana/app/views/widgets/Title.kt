package eu.codlab.lorcana.app.views.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import eu.codlab.lorcana.app.theme.LocalDarkTheme
import eu.codlab.lorcana.app.theme.MyApplicationTheme

private val title = 32.sp
private val subtitle = 24.sp
private val normal = 16.sp

private data class PairColor(
    val dark: Color,
    val light: Color
)

private val titleColor = PairColor(
    dark = Color.White,
    light = Color.Black
)

private val subtitleColor = PairColor(
    dark = Color(0xff8f959f),
    light = Color(0xff343434)
)

@Composable
fun TextTitle(
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    fontSize: TextUnit = title,
    color: Color? = null
) = CreateText(
    text,
    textAlign,
    fontSize,
    FontWeight.Bold,
    LocalDarkTheme.current,
    titleColor,
    color
)

@Composable
fun TextSubtitle(
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    fontSize: TextUnit = subtitle,
    color: Color? = null
) = CreateText(
    text,
    textAlign,
    fontSize,
    FontWeight.Bold,
    LocalDarkTheme.current,
    subtitleColor,
    color
)

@Composable
fun TextNormal(
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    fontSize: TextUnit = normal,
    color: Color? = null,
    fontWeight: FontWeight = FontWeight.Normal
) = CreateText(
    text,
    textAlign,
    fontSize,
    fontWeight,
    LocalDarkTheme.current,
    titleColor,
    color
)

@Composable
private fun CreateText(
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    fontSize: TextUnit,
    fontWeight: FontWeight = FontWeight.Bold,
    isDark: Boolean = LocalDarkTheme.current,
    pair: PairColor,
    color: Color?
) {
    val actual = color ?: if (isDark) pair.dark else pair.light

    return Text(
        text = text,
        textAlign = textAlign,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = actual
    )
}

@Preview
@Composable
private fun RenderTextDark() {
    MyApplicationTheme(darkTheme = true) {
        Column(modifier = Modifier.systemBackground()) {
            TextTitle(text = "title")
            TextSubtitle(text = "subtitle")
            TextNormal(text = "text")
        }
    }
}

@Preview
@Composable
private fun RenderTextLight() {
    MyApplicationTheme(darkTheme = false) {
        Column(modifier = Modifier.systemBackground()) {
            TextTitle(text = "title")
            TextSubtitle(text = "subtitle")
            TextNormal(text = "text")
        }
    }
}
