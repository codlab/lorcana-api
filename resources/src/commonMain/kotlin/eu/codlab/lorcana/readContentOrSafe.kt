@file:Suppress("ktlint:filename")

package eu.codlab.lorcana

import dev.icerock.moko.resources.FileResource

expect fun FileResource.readContentOrSafe(): String?
