package com.github.codlab.lorcana.files

import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

actual val RootPath = cachePath() //StandardPaths.resourcesFolder

fun cachePath(): String {
    val result = NSSearchPathForDirectoriesInDomains(
        NSCachesDirectory,
        NSUserDomainMask,
        true
    )

    return "${result[0]}"
}
