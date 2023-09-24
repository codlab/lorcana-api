@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")

package eu.codlab.lorcana.app.views.home

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
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
import com.github.codlab.lorcana.card.Card
import eu.codlab.lorcana.app.theme.AppColor
import eu.codlab.lorcana.app.theme.MyApplicationTheme
import eu.codlab.lorcana.app.theme.WindowSize
import eu.codlab.lorcana.app.utils.rememberViewModel

val LocalWindow = compositionLocalOf { WindowSize.COMPACT }
val LocalCards: ProvidableCompositionLocal<List<Card>> = compositionLocalOf { emptyList() }
val LocalApp = compositionLocalOf { AppModel() }

@Suppress("LongMethod") // interestingly, detekt shows APp is 67 length long
@Composable
fun App(isDarkTheme: Boolean) {
    val localDensity = LocalDensity.current
    var window by remember { mutableStateOf(WindowSize.COMPACT) }
    var cards by remember { mutableStateOf(emptyList<Card>()) }
    val model = rememberViewModel { AppModel() }

    val state by model.states.collectAsState()
    println("${state.loading} ${state.loggedIn} $window")

    cards = state.cards

    CompositionLocalProvider(
        LocalWindow provides window,
        LocalCards provides cards,
        LocalApp provides model
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
                    AppContent()
                }
            }
        }
    }
}
