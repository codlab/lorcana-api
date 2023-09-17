package eu.codlab.lorcana.app.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

expect fun Modifier.navigationBarsPadding(): Modifier
expect fun Modifier.statusBarsPadding(): Modifier

expect interface WindowInsetsController {
    /**
     * The status bars icon + content will change to a dark color if [dark] is true.
     * This is appropriate when the background is light.
     *
     * * In Android: This setting is ignored on API <23.
     * * In iOS: Use with `WindowInsetsUIViewController`. If the current `UIViewController`
     * is not the main one in the window, this setting is ignored.
     */
    fun setStatusBarContentColor(dark: Boolean)

    /**
     * The navigation bars icons will change to a dark color if [dark] is true.
     * This is appropriate when the background is light.
     *
     * * In Android: This setting is ignored on API <26 or on the gesture
     * navigation mode.
     * * In iOS: This setting is ignored.
     */
    fun setNavigationBarsContentColor(dark: Boolean)

    /**
     * Change the visibility of the status bars.
     */
    fun setIsStatusBarsVisible(isVisible: Boolean)

    /**
     * Change the visibility of the navigation bars.
     */
    fun setIsNavigationBarsVisible(isVisible: Boolean)
}

@Composable
expect fun rememberWindowInsetsController(): WindowInsetsController?
