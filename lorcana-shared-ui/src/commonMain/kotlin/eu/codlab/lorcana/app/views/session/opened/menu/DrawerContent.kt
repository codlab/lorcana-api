package eu.codlab.lorcana.app.views.session.opened.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.codlab.lorcana.app.theme.MyApplicationTheme
import eu.codlab.lorcana.app.views.widgets.systemBackground

@Composable
fun DrawerContent(
    modifier: Modifier = Modifier.width(250.dp)
) {
    Column(
        modifier = modifier
            .systemBackground()
    ) {
        DrawerTitle(text = "Titre 1")
        DrawerItem(text = "Item 1")
        DrawerItem(text = "Item 2")
        DrawerItem(text = "Item 3")
        DrawerItem(text = "Item 4")
        DrawerItem(text = "Item 5")
        DrawerSeparator()
        DrawerTitle(text = "Titre 1")
        DrawerItem(text = "Item 1")
        DrawerItem(text = "Item 2")
        DrawerItem(text = "Item 3")
        DrawerSeparator()
        DrawerTitle(text = "Titre 1")
        DrawerItem(text = "Item 1")
        DrawerItem(text = "Item 2")
        DrawerItem(text = "Item 3")
        DrawerSeparator()
        DrawerTitle(text = "Titre 1")
        DrawerItem(text = "Item 1")
        DrawerItem(text = "Item 2")
        DrawerSeparator()
        DrawerTitle(text = "Titre 1")
        DrawerItem(text = "Item 1")
        DrawerItem(text = "Item 2")
        DrawerItem(text = "Item 3")
    }
}

@Preview
@Composable
fun PreviewDrawerContentLight() {
    MyApplicationTheme(
        darkTheme = false
    ) {
        DrawerContent()
    }
}

@Preview
@Composable
fun PreviewDrawerContentDark() {
    MyApplicationTheme(
        darkTheme = true
    ) {
        DrawerContent()
    }
}
