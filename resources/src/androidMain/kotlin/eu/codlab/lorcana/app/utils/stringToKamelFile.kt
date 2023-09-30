@file:Suppress("ktlint:filename")

package eu.codlab.lorcana.app.utils

import io.kamel.core.utils.File

actual fun stringToKamelFile(string: String): File? {
    return File(string)
}
