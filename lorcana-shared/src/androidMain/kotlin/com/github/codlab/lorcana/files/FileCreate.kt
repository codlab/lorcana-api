package com.github.codlab.lorcana.files

actual suspend fun touch(path: String) {
    val vfs = VirtualFile(path)
    vfs.touch()
}