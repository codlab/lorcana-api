package eu.codlab.lorcana.app.views.widgets

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable

@get:Composable
@get:NonRestartableComposable
expect val WindowInsets.Companion.topBar: WindowInsets

@get:Composable
@get:NonRestartableComposable
expect val WindowInsets.Companion.startBar: WindowInsets

@get:Composable
@get:NonRestartableComposable
expect val WindowInsets.Companion.endBar: WindowInsets

@get:Composable
@get:NonRestartableComposable
expect val WindowInsets.Companion.bottomBar: WindowInsets
