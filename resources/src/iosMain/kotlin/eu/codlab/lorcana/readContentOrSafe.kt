@file:Suppress("ktlint:filename")

package eu.codlab.lorcana

import dev.icerock.moko.resources.FileResource
import platform.Foundation.NSBundle
import platform.darwin.NSObject
import platform.darwin.NSObjectMeta

actual fun FileResource.readContentOrSafe(): String? {
    @Suppress("TooGenericExceptionCaught", "PrintStackTrace")
    return try {
        println("bundle was   ${this.bundle.bundlePath}")
        println(" and its url ${this.bundle.bundleURL}")
        println(" and its ID  ${this.bundle.bundleIdentifier}")

        val reader = ResourceReader()
        val bundle = NSBundle.bundleForClass(BundleMarker)

        println("attempt to read inside the reader...")
        val path =
            "Frameworks/resources.framework/${this.bundle.bundleIdentifier}:resources.bundle/Contents/Resources/files"
        println("reading inside $path")

        reader.readFiles(bundle, path)
        reader.readResource(bundle, "${this.fileName}.${this.extension}", path)
    } catch (e: Throwable) {
        println("exception in readContentSafe() $e")
        e.printStackTrace()
        null
    }
}

@Suppress("UnusedPrivateClass")
private class BundleMarker : NSObject() {
    companion object : NSObjectMeta()
}
