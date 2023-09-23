@file:Suppress("MagicNumber")

package eu.codlab.lorcana.app.views.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.codlab.lorcana.app.theme.LocalDarkTheme
import eu.codlab.lorcana.app.theme.MyApplicationTheme
import eu.codlab.lorcana.app.views.widgets.TextNormal
import eu.codlab.lorcana.app.views.widgets.TextSubtitle

@Composable
fun DrawerSeparator(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .systemBackground()
            .padding(start = 24.dp, end = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier
                .height(1.dp)
                .systemBackground(),
            color = colorDimmed() ?: Color.Black
        )
    }
}

@Composable
fun DrawerTitle(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean = false
) {
    Row(
        modifier = modifier
            .height(32.dp)
            .fillMaxWidth()
            .systemBackground(selected = selected)
            .padding(start = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextSubtitle(
            fontSize = 18.sp,
            text = text,
            color = color(selected)
        )
    }
}

@Composable
fun DrawerItem(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean = false
) {
    Row(
        modifier = modifier
            .height(32.dp)
            .fillMaxWidth()
            .systemBackground(selected = selected)
            .padding(start = 32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextNormal(
            text = text,
            color = color(selected)
        )
    }
}

@Composable
fun color(selected: Boolean = false): Color? {
    if (selected) {
        return if (LocalDarkTheme.current) {
            Color(0xFFFFFFFF)
        } else {
            Color(0xFF3A3A3A)
        }
    }

    return if (LocalDarkTheme.current) {
        Color(0xFFFFFFFF)
    } else {
        Color(0xFF3A3A3A)
    }
}

@Composable
fun colorDimmed(): Color {
    return if (LocalDarkTheme.current) {
        Color(0x33FFFFFF)
    } else {
        Color(0x333A3A3A)
    }
}

@Composable
fun Modifier.systemBackground(selected: Boolean = false): Modifier {
    return this.background(
        if (selected) {
            if (LocalDarkTheme.current) {
                Color(0xFF242424)
            } else {
                Color(0xFF9E9E9E)
            }
        } else if (LocalDarkTheme.current) {
            Color.Black
        } else {
            Color.White
        }
    )
}

@Preview(widthDp = 150)
@Composable
fun DrawerItemPreviewDark() {
    MyApplicationTheme(darkTheme = true) {
        Column(modifier = Modifier.fillMaxWidth()) {
            DrawerTitle(text = "Title")
            DrawerSeparator()
            DrawerItem(text = "Item")
            DrawerItem(text = "Item", selected = true)
        }
    }
}

@Preview(widthDp = 150)
@Composable
fun DrawerItemPreviewLight() {
    MyApplicationTheme(darkTheme = false) {
        Column(modifier = Modifier.fillMaxWidth()) {
            DrawerTitle(text = "Title")
            DrawerSeparator()
            DrawerItem(text = "Item")
            DrawerItem(text = "Item", selected = true)
        }
    }
}
