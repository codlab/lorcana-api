package com.github.codlab.lorcana.files

import korlibs.io.file.VfsFile

actual suspend fun touch(path: String) {
    val vfs = VirtualFile(path)
    vfs.touch()
}