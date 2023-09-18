package eu.codlab.lorcana.app.views.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import eu.codlab.lorcana.app.theme.MyApplicationTheme
import eu.codlab.lorcana.app.utils.FontIcon
import eu.codlab.lorcana.app.views.home.LocalDarkTheme

@Composable
fun TransparentIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    fontFamily: FontFamily,
    text: String,
    color: Color? = null,
    fontSize: TextUnit = TextUnit.Unspecified
) {
    val newColor = color
        ?: if (LocalDarkTheme.current) {
            Color.White
        } else {
            Color.Black
        }

    TextButton(
        modifier = modifier,
        onClick = onClick,
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            fontSize = fontSize,
            color = newColor,
            text = text,
            fontFamily = fontFamily
        )
    }
}

@Composable
private fun TransparentButtonPreview() {
    val modifier = Modifier.fillMaxWidth()
    Column(
        modifier = Modifier.systemBackground()
    ) {
        TransparentIconButton(
            modifier = modifier,
            onClick = {},
            fontFamily = FontIcon.fontFamily(),
            text = "l"
        )
    }
}

@Preview(widthDp = 250)
@Composable
private fun TransparentButtonPreviewDark() =
    MyApplicationTheme(darkTheme = true) { TransparentButtonPreview() }

@Preview(widthDp = 250)
@Composable
private fun TransparentButtonPreviewLight() =
    MyApplicationTheme(darkTheme = false) { TransparentButtonPreview() }
