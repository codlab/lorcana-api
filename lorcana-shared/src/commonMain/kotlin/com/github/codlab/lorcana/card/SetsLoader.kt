package com.github.codlab.lorcana.card

import com.github.codlab.lorcana.shared.SharedRes
import eu.codlab.moko.ext.safelyReadContent

class SetsLoader {
    suspend fun load(): List<LorcanaSet> {
        val textSets = SharedRes.files.sets.safelyReadContent()
        val sets = LorcanaSetObject.fromArray(textSets)

        return listOf(
            Pair(SharedRes.files.tfc, "tfc"),
            Pair(SharedRes.files.d23, "d23"),
            Pair(SharedRes.files.rotf, "rotf")
        ).mapNotNull { fileLang ->
            val file = fileLang.first
            val lang = fileLang.second

            return@mapNotNull sets
                .find { it.setCode.lowercase() == lang }
                ?.let { lorcanaSet ->
                    LorcanaSet(
                        LorcanaHolder.fromContent(file.safelyReadContent()),
                        lorcanaSet = lorcanaSet
                    )
                }
        }
    }
}
