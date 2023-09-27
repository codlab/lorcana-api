package eu.codlab.lorcana

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.Foundation.NSBundle
import platform.Foundation.NSError
import platform.Foundation.NSFileManager
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithContentsOfFile

@Suppress("TooGenericExceptionCaught", "PrintStackTrace")
class ResourceReader {
    @OptIn(ExperimentalForeignApi::class)
    fun readFiles(bundle: NSBundle, path: String) {
        println("reading files in $path")
        try {
            memScoped {
                val errorPtr2 = alloc<ObjCObjectVar<NSError?>>()

                val list = NSFileManager.defaultManager.contentsOfDirectoryAtPath(
                    "${bundle.bundlePath}/$path",
                    errorPtr2.ptr
                )

                println(list)
                list?.forEach {
                    println("now reading for : $it")
                    readFiles(bundle, "$path/$it")
                }
            }
        } catch (e: Throwable) {
            println("$e")
        }
    }

    @OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
    fun readResource(bundle: NSBundle, name: String, originalPath: String): String {
        readFiles(bundle, "Frameworks")
        val (filename, type) = when (val lastPeriodIndex = name.lastIndexOf('.')) {
            0 -> {
                null to name.drop(1)
            }

            in 1..Int.MAX_VALUE -> {
                name.take(lastPeriodIndex) to name.drop(lastPeriodIndex + 1)
            }

            else -> {
                name to null
            }
        }

        val listingWrongPathResource = listOfNotNull(
            filename,
            type
        ).joinToString(".")

        val path = bundle.pathForResource(
            name = filename,
            ofType = type,
            inDirectory = originalPath
        ) ?: error(
            "Couldn't get path of $name (parsed as: $listingWrongPathResource)"
        )

        return memScoped {
            val errorPtr = alloc<ObjCObjectVar<NSError?>>()

            NSString.stringWithContentsOfFile(
                path,
                encoding = NSUTF8StringEncoding,
                error = errorPtr.ptr
            ) ?: run {
                error("Couldn't load resource: $name. Error: ${errorPtr.value?.localizedDescription} - ${errorPtr.value}")
            }
        }
    }
}
