package eu.codlab.lorcana.app.utils

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun StringResource.localized(): String {
    return stringResource(resource = this)
}
