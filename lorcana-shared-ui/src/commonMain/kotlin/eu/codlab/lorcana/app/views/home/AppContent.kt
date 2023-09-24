@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")

package eu.codlab.lorcana.app.views.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eu.codlab.lorcana.app.views.init.InitializeScreen
import eu.codlab.lorcana.app.views.session.opened.page.principal.MainPageScreen
import eu.codlab.lorcana.app.views.session.opened.page.single.SingleCard
import eu.codlab.lorcana.app.views.widgets.BottomSpacer
import eu.codlab.lorcana.app.views.widgets.SafeArea
import eu.codlab.lorcana.app.views.widgets.SafeAreaBehavior
import eu.codlab.lorcana.app.views.widgets.TopSpacer
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
        // Define a scene to the navigation graph
        scene(
            // Scene's route path
            route = "/home",
            // Navigation transition for this scene, this is optional
            navTransition = NavTransition()
        ) {
            SideEffect {
                println("switch to initialize")
                currentPage = Pages.Initialize
            }

            LaunchedEffect(state) {
                println("having $state")
                if (state.initialized) {
                    navigator.navigate(
                        route = "/main",
                        options = NavOptions(
                            launchSingleTop = true
                        )
                    )
                }
            }
            InitializeScreen(
                LocalApp.current,
                Modifier.fillMaxSize()
            )
        }

        scene(
            // Scene's route path
            route = "/main",
            // Navigation transition for this scene, this is optional
            navTransition = NavTransition()
        ) {
            SideEffect {
                println("switch to main")
                currentPage = Pages.Main
            }

            SafeArea(
                behavior = remember {
                    SafeAreaBehavior(
                        extendToBottom = true
                    )
                }
            ) {
                MainPageScreen {
                    navigator.navigate(
                        route = "/show/card/${it.cardNumber}",
                        options = NavOptions(
                            popUpTo = PopUpTo(
                                route = "/main",
                                inclusive = false
                            )
                        )
                    )
                }
            }
        }

        scene(
            // Scene's route path
            route = "/show/card/{cardId}",
            swipeProperties = SwipeProperties()
        ) { backStackEntry ->
            SideEffect {
                println("switch to single")
                currentPage = Pages.SingleCards
            }

            println(backStackEntry.path)
            val value = backStackEntry.pathMap["cardId"]
            value?.let { cardId ->
                val cards = state.cards
                val card = cards.find { it.cardNumber == cardId.toInt() }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    Arrangement.spacedBy(5.dp)
                ) {
                    TopSpacer()
                    SingleCard(card!!)
                    BottomSpacer()
                }
            }
        }
    }
}
