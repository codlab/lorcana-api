package eu.codlab.lorcana.app.views.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import eu.codlab.lorcana.app.theme.LocalDarkTheme
import eu.codlab.lorcana.app.theme.MyApplicationTheme
import eu.codlab.lorcana.app.utils.FontIcon

@Composable
fun TransparentIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    imageVector: ImageVector,
    color: Color? = null
) {
    // TODO getting the color from local context
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
        Image(
            modifier = modifier,
            imageVector = imageVector,
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                color = newColor
            )
        )
    }
}

@Composable
fun TransparentIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    fontFamily: FontFamily,
    text: String,
    color: Color? = null,
    fontSize: TextUnit = TextUnit.Unspecified
) {
    // TODO getting the color from local context
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

        TransparentIconButton(
            modifier = modifier,
            onClick = {},
            imageVector = Icons.Default.Menu
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
