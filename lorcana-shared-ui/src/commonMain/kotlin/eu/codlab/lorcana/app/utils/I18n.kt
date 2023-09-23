package eu.codlab.lorcana.app.utils

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.StringResource

@Composable
fun i18n(stringResource: StringResource) {
    println("unused $stringResource")
    // StringDesc.Resource(stringResource).localized()
    // TODO implementation for js :x
}
