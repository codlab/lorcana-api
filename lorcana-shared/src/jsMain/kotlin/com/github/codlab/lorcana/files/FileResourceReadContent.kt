package com.github.codlab.lorcana.files

import dev.icerock.moko.resources.FileResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

actual suspend fun FileResource.readContent(): String = withContext(Dispatchers.Default) {
    return@withContext getText()
}
