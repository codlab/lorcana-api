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
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.github.codlab.lorcana.card.Card
import eu.codlab.lorcana.app.theme.LocalThemeEnvironment
import eu.codlab.lorcana.app.theme.MyApplicationTheme
import eu.codlab.lorcana.app.views.session.opened.page.principal.cards.CardsList
import eu.codlab.lorcana.app.views.session.opened.page.principal.cards.Decks
import eu.codlab.lorcana.app.views.session.opened.page.principal.cards.LoreCounter
import eu.codlab.lorcana.app.views.widgets.BottomSpacer
import eu.codlab.lorcana.app.views.widgets.TextNormal

@Composable
fun MainPageScreen(onCard: (Card) -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        Arrangement.spacedBy(5.dp)
    ) {
        TabNavigator(CardsList(onCard)) {
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
                bottomBar = { BottomBar(onCard) }
            )
        }
    }
}

@Composable
fun BottomBar(onCard: (Card) -> Unit) {
    Column(
        modifier = Modifier.background(
            color = LocalThemeEnvironment.current.navigationColors.background
        )
    ) {
        BottomNavigation(backgroundColor = Color.Transparent) {
            TabNavigationItem(LoreCounter)
            TabNavigationItem(CardsList(onCard))
            TabNavigationItem(Decks)
        }

        BottomSpacer()
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    val isSelected = tabNavigator.current.options.index == tab.options.index

    println("isSelected/$isSelected tabNavigator ${tabNavigator.current.options.index} ${tab.options.index}")

    val tint = if (isSelected) {
        LocalThemeEnvironment.current.navigationColors.selected
    } else {
        LocalThemeEnvironment.current.navigationColors.unselected
    }
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
                tint = tint
            )
        },
        label = {
            TextNormal(
                text = tab.options.title,
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
