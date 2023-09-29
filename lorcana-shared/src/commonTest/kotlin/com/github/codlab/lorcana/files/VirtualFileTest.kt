package com.github.codlab.lorcana.files

import kotlin.test.Test
import kotlin.test.assertEquals

class VirtualFileTest {

    @Test
    fun testName() {
        val fileName = "fileName"
        val vfsFile = VirtualFile(fileName)
        assertEquals(fileName, vfsFile.absolutePath)

        val vfsFileFromVirtualFile = VirtualFile(vfsFile)
        assertEquals(fileName, vfsFileFromVirtualFile.absolutePath)

        listOf(
            VirtualFile("path", fileName),
            VirtualFile(VirtualFile("path"), fileName)
        ).forEach {
            assertEquals("path/$fileName", it.absolutePath)
        }
    }
}