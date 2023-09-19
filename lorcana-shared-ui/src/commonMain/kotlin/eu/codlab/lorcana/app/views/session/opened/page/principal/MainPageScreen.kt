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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import eu.codlab.lorcana.app.theme.LocalNavigationColors
import eu.codlab.lorcana.app.theme.MyApplicationTheme
import eu.codlab.lorcana.app.views.session.opened.page.principal.cards.CardsList
import eu.codlab.lorcana.app.views.session.opened.page.principal.cards.Decks
import eu.codlab.lorcana.app.views.session.opened.page.principal.cards.LoreCounter
import eu.codlab.lorcana.app.views.widgets.TextNormal

internal class MainPageScreen : Screen {
    override val key: ScreenKey
        get() = "MainPageScreen"

    @Composable
    override fun Content() {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            Arrangement.spacedBy(5.dp)
        ) {
            TabNavigator(CardsList()) {
                Scaffold(
                    content = {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                        ) {
                            CurrentScreen()
                        }
                    },
                    bottomBar = {
                        BottomNavigation(
                            backgroundColor = LocalNavigationColors.current.background
                        ) {
                            TabNavigationItem(LoreCounter)
                            TabNavigationItem(CardsList())
                            TabNavigationItem(Decks)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    val isSelected = tabNavigator.current == tab
    BottomNavigationItem(
        selected = isSelected,
        onClick = { tabNavigator.current = tab },
        icon = {
            Icon(
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp),
                painter = tab.options.icon!!,
                contentDescription = tab.options.title,
                tint = if (isSelected) {
                    LocalNavigationColors.current.selected
                } else {
                    LocalNavigationColors.current.unselected
                }
            )
        },
        label = {
            TextNormal(text = tab.options.title, color = LocalNavigationColors.current.selected)
        }
    )
}

@Preview(widthDp = 200, heightDp = 300)
@Composable
fun CardsListLightPreview() {
    MyApplicationTheme(darkTheme = false) {
        Column(Modifier.background(Color.Black)) {
            Navigator(MainPageScreen()) {
                CurrentScreen()
            }
        }
    }
}
