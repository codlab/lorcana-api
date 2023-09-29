package com.github.codlab.lorcana.files

import platform.Foundation.NSFileManager

actual suspend fun touch(path: String) {
    NSFileManager.defaultManager.createFileAtPath(path, null, null)
}