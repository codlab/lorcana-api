package eu.codlab.lorcana.app.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.moriatsushi.insetsx.WindowInsetsController
import com.moriatsushi.insetsx.navigationBarsPadding
import com.moriatsushi.insetsx.statusBarsPadding

actual fun Modifier.navigationBarsPadding(): Modifier = navigationBarsPadding()
actual fun Modifier.statusBarsPadding(): Modifier = statusBarsPadding()

@Composable
actual fun rememberWindowInsetsController(): WindowInsetsController? =
    com.moriatsushi.insetsx.rememberWindowInsetsController()

actual typealias WindowInsetsController = com.moriatsushi.insetsx.WindowInsetsController
