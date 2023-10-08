@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")

package eu.codlab.lorcana.app.views.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eu.codlab.compose.widgets.BottomSpacer
import eu.codlab.compose.widgets.TopSpacer
import eu.codlab.lorcana.app.views.init.InitializeScreen
import eu.codlab.lorcana.app.views.session.opened.page.principal.MainPageScreen
import eu.codlab.lorcana.app.views.session.opened.page.single.InvalidCard
import eu.codlab.lorcana.app.views.session.opened.page.single.SingleCard
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.PopUpTo
import moe.tlaster.precompose.navigation.SwipeProperties
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition

@Composable
@Suppress("LongMethod")
fun AppContent() {
    var currentPage by remember { mutableStateOf(Pages.Initialize) }
    val model = LocalApp.current
    val state by model.states.collectAsState()

    val navigator = rememberNavigator()

    NavHost(
        // Assign the navigator to the NavHost
        navigator = navigator,
        // Navigation transition for the scenes in this NavHost, this is optional
        navTransition = NavTransition(),
        // The start destination
        initialRoute = "/home"
    ) {
        scene(
            // Scene's route path
            route = "/home",
            // Navigation transition for this scene, this is optional
            navTransition = NavTransition()
        ) {
            val currentState by model.states.collectAsState()

            if (!currentState.initialized) {
                InitializeScreen(
                    LocalApp.current,
                    Modifier.fillMaxSize()
                )
            } else {
                SideEffect {
                    println("switch to main")
                    currentPage = Pages.Main
                }
                MainPageScreen {
                    navigator.navigate(
                        route = "/show/card/${it.setCode}/${it.number}",
                        options = NavOptions(
                            popUpTo = PopUpTo(
                                route = "/home",
                                inclusive = false
                            ),
                            launchSingleTop = false
                        )
                    )
                }
            }
        }

        scene(
            // Scene's route path
            route = "/show/card/{setId}/{cardId}",
            swipeProperties = SwipeProperties()
        ) { backStackEntry ->
            SideEffect {
                println("switch to single")
                currentPage = Pages.SingleCards
            }

            println(backStackEntry.path)
            val cardId = backStackEntry.pathMap["cardId"] ?: return@scene
            val setId = backStackEntry.pathMap["setId"] ?: return@scene

            val set = state.cardSets.find { it.code() == setId } ?: return@scene
            val card = set.cards().find { it.number == cardId.toInt() }

            Column(
                modifier = Modifier.fillMaxSize(),
                Arrangement.spacedBy(5.dp)
            ) {
                TopSpacer()
                if (null != card) {
                    SingleCard(card)
                } else {
                    InvalidCard()
                }
                BottomSpacer()
            }
        }
    }
}
