package eu.codlab.lorcana.app.views.session.opened.page.principal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.codlab.lorcana.card.LorcanaCard
import eu.codlab.compose.theme.LocalThemeEnvironment
import eu.codlab.compose.widgets.BottomSpacer
import eu.codlab.compose.widgets.TextNormal
import eu.codlab.lorcana.app.theme.MyApplicationTheme
import eu.codlab.lorcana.app.views.session.opened.page.principal.cards.CardsList
import eu.codlab.lorcana.app.views.session.opened.page.principal.cards.Decks
import eu.codlab.lorcana.app.views.session.opened.page.principal.cards.LoreCounter
import eu.codlab.lorcana.app.views.session.opened.page.principal.cards.Tab

@Composable
fun MainPageScreen(onCard: (LorcanaCard) -> Unit) {
    val tabs = remember {
        listOf(
            LoreCounter(),
            CardsList(onCard),
            Decks()
        )
    }
    val tab = remember { mutableStateOf(tabs[1]) }

    Column(
        Modifier
            .fillMaxSize(),
        Arrangement.spacedBy(5.dp)
    ) {
        Scaffold(
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    tab.value.Content()
                }
            },
            bottomBar = {
                BottomBar(tabs, tab.value) {
                    tab.value = it
                }
            }
        )
    }
}

@Composable
fun BottomBar(
    tabs: List<Tab>,
    currentTab: Tab,
    onTab: (Tab) -> Unit
) {
    Surface(
        elevation = 24.dp
    ) {
        Column(
            modifier = Modifier.background(
                color = LocalThemeEnvironment.current.navigationColors.background
            )
        ) {
            BottomNavigation(backgroundColor = Color.Transparent) {
                tabs.map {
                    TabNavigationItem(it, it.option.index == currentTab.option.index, onTab)
                }
            }

            BottomSpacer()
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab, isSelected: Boolean, onTab: (Tab) -> Unit) {
    val tint = if (isSelected) {
        LocalThemeEnvironment.current.navigationColors.selected
    } else {
        LocalThemeEnvironment.current.navigationColors.unselected
    }
    BottomNavigationItem(
        selected = isSelected,
        onClick = { onTab(tab) },
        icon = {
            Icon(
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp),
                painter = tab.option.icon!!,
                contentDescription = tab.option.title,
                tint = tint
            )
        },
        label = {
            TextNormal(
                text = tab.option.title,
                color = tint
            )
        }
    )
}

@Preview(widthDp = 200, heightDp = 300)
@Composable
fun CardsListLightPreview() {
    MyApplicationTheme(darkTheme = false) {
        MainPageScreen {}
    }
}
