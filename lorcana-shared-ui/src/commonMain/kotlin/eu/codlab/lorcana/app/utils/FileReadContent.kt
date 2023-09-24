package eu.codlab.lorcana.app.utils

import com.github.codlab.lorcana.files.readContent
import dev.icerock.moko.resources.FileResource
import eu.codlab.lorcana.readContentOrSafe

suspend fun FileResource.safelyReadContent(): String {
    @Suppress("TooGenericExceptionCaught", "PrintStackTrace")
    return try {
        readContent()
    } catch (e: Throwable) {
        println("exception in safelyReadContent $e")
        e.printStackTrace()

        readContentOrSafe() ?: ""
    }
}
