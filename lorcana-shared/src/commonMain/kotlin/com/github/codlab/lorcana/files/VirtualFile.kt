package com.github.codlab.lorcana.files

import korlibs.io.file.Vfs
import korlibs.io.file.VfsFile
import korlibs.io.file.std.localVfs
import korlibs.time.DateTime

class VirtualFile {

    companion object {
        val Root = VirtualFile(RootPath)
    }

    private val path: String
    private val file: String?
    private val vfs: VfsFile

    constructor(file: String) {
        path = file
        this.file = null
        this.vfs = localVfs(path, true)
    }

    constructor(path: String, file: String) {
        this.path = path
        this.file = file
        this.vfs = localVfs("$path/$file", true)
    }

    constructor(virtualFile: VirtualFile) {
        path = virtualFile.path
        file = virtualFile.file
        vfs = localVfs("$path/$file", true)
    }

    constructor(virtualFile: VirtualFile, file: String) : this(virtualFile.absolutePath, file)

    val absolutePath: String
        get() = if (null != file) {
            "$path/$file"
        } else {
            path
        }

    suspend fun mkdirs() {
        vfs.mkdirs()
    }

    suspend fun readString(): String {
        return vfs.readString()
    }

    suspend fun read(): ByteArray {
        return vfs.read()
    }

    suspend fun exists(): Boolean {
        val exists = vfs.exists()
        println("internal ${vfs.absolutePath} exists/$exists")
        return exists
    }

    suspend fun write(bytes: ByteArray) {
        vfs.write(bytes)
    }

    suspend fun touch() {
        vfs.touch(DateTime.now())
    }
}