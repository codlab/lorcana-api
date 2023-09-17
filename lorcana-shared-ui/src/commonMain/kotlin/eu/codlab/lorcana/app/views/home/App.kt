package eu.codlab.lorcana.app.views.home

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.github.codlab.lorcana.card.Card
import eu.codlab.lorcana.app.theme.AppColor
import eu.codlab.lorcana.app.theme.MyApplicationTheme
import eu.codlab.lorcana.app.theme.WindowSize
import eu.codlab.lorcana.app.utils.rememberViewModel
import eu.codlab.lorcana.app.views.init.InitializeScreen
import eu.codlab.lorcana.app.views.session.opened.OpenedSessionScreen

private lateinit var GlobalApp_: AppModel

internal val Screen.GlobalApp: AppModel
    get() = GlobalApp_

val LocalWindow = compositionLocalOf { WindowSize.COMPACT }
val LocalDarkTheme = compositionLocalOf { false }
val LocalCards: ProvidableCompositionLocal<List<Card>> = compositionLocalOf { emptyList() }

@Composable
fun App(
    isDarkTheme: Boolean,
) {
    val localDensity = LocalDensity.current
    var window by remember { mutableStateOf(WindowSize.COMPACT) }
    val currentTheme = LocalDarkTheme.current
    var darkTheme by remember { mutableStateOf(currentTheme) }
    var cards by remember { mutableStateOf(emptyList<Card>()) }

    val model = rememberViewModel {
        AppModel("", "")
    }

    DisposableEffect(isDarkTheme) {
        println("new status for $isDarkTheme")
        darkTheme = isDarkTheme

        onDispose {
            // nothing
        }
    }

    GlobalApp_ = model

    println("initialize screen $window $darkTheme")

    val state by model.states.collectAsState()
    println("${state.loading} ${state.loggedIn} $window")

    cards = state.cards

    CompositionLocalProvider(
        LocalWindow provides window,
        LocalDarkTheme provides darkTheme,
        LocalCards provides cards
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned { coordinates ->
                    val width = with(localDensity) { coordinates.size.width.toDp() }

                    val newWindowSize = WindowSize.basedOnWidth(width)
                    if (newWindowSize != window) {
                        window = newWindowSize
                    }

                    println("windowSize $window")
                }
                .background(AppColor.BackgroundBlue)
        ) {
            MyApplicationTheme(
                darkTheme = isDarkTheme
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            if (isSystemInDarkTheme()) {
                                Color.Black
                            } else {
                                Color.White
                            }
                        )
                ) {
                    Navigator(InitializeScreen()) { navigator ->
                        LaunchedEffect(state) {
                            println("having $state")
                            if (!state.initialized) {
                                navigator.popUntilRoot()
                                navigator.replace(InitializeScreen())
                            } else {
                                navigator.popUntilRoot()
                                navigator.replace(OpenedSessionScreen())
                            }
                        }
                        CurrentScreen()
                    }
                }
            }
        }
    }
}
