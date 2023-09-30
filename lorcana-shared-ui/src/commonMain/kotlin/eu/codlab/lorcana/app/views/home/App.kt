@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")

package eu.codlab.lorcana.app.views.home

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
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
import eu.codlab.lorcana.app.theme.AppColor
import eu.codlab.lorcana.app.theme.MyApplicationTheme
import eu.codlab.lorcana.app.theme.WindowSize
import eu.codlab.lorcana.app.utils.rememberViewModel
import eu.codlab.lorcana.downloader.DownloadAssets

val LocalWindow = compositionLocalOf { WindowSize.COMPACT }
val LocalApp = compositionLocalOf { AppModel() }
val LocalDownloader = compositionLocalOf { DownloadAssets() }

@Suppress("LongMethod") // interestingly, detekt shows App is 67 length long
@Composable
fun App(isDarkTheme: Boolean) {
    val currentAppModel = LocalApp.current
    val localDensity = LocalDensity.current

    var window by remember { mutableStateOf(WindowSize.COMPACT) }
    val model = rememberViewModel { currentAppModel }

    val downloader by remember { mutableStateOf(DownloadAssets()) }

    val state by model.states.collectAsState()
    println("${state.loading} ${state.loggedIn} $window")

    LaunchedEffect(downloader) {
        println("starting downloading assets...")
        downloader.start()
    }

    CompositionLocalProvider(
        LocalWindow provides window,
        LocalApp provides model,
        LocalDownloader provides downloader
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
