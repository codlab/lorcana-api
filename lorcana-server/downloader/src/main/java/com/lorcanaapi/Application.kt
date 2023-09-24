package com.lorcanaapi

import com.github.codlab.lorcana.card.Card
import com.github.codlab.lorcana.files.readContent
import com.github.codlab.lorcana.shared.SharedRes
import com.jcabi.manifests.Manifests
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.Url
import io.ktor.util.cio.writeChannel
import io.ktor.utils.io.copyAndClose
import kotlinx.coroutines.runBlocking
import net.coobird.thumbnailator.ThumbnailParameter
import net.coobird.thumbnailator.Thumbnails
import net.coobird.thumbnailator.name.Rename
import java.io.File

var cards: List<Card> = emptyList()

val client = HttpClient(CIO) {
}

private val ExpectedSizeImage = Pair(186, 260)

fun main(arg: Array<String>) {
    cards = runBlocking {
        val textCards = SharedRes.files.allCards.readContent()
        return@runBlocking Card.fromArray(textCards)
    }

    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    val rootDir = try {
        Manifests.read("RootPath")
    } catch (e: Throwable) {
        System.getProperty("rootPath")
    }

    println("args ? $rootDir")

    runBlocking {
        cards.forEach { card ->
            println("doing ${card.setCode}/${card.cardNumber}")
            val imagesFolder = File(rootDir, "src/data/images")

            listOf(imagesFolder).forEach {
                if (!it.exists()) it.mkdirs()
            }

            val urls = card.imageUrls
            listOf(
                // not downloading the foil, large & medium due to oversize
                // Pair(urls.foil, "foil"),
                // Pair(urls.large, "large"),
                // Pair(urls.medium, "medium"),
                Pair(urls.small, "${card.setCode}_normal_small_${card.cardNumber}@1x.png")
            ).forEach {
                downloadImage(it.first, rootDir, it.second)
            }

            downloadImage(
                urls.foil,
                rootDir,
                "${card.setCode}_foil_large_${card.cardNumber}@1x.png",
                "src/data/foils"
            )

            @Suppress("SpreadOperator")
            Thumbnails.of(*File(rootDir, "src/data/foils").listFiles())
                .size(ExpectedSizeImage.first, ExpectedSizeImage.second)
                .outputFormat("png")
                .toFiles(
                    imagesFolder,
                    ChangeName {
                        it?.replace("foil_large", "foil_small") ?: ""
                    }
                )
        }
    }
}

suspend fun downloadImage(
    url: String,
    root: String,
    name: String,
    path: String = "src/data/images"
) {
    val folder = File(root, path)
    if (!folder.exists()) folder.mkdirs()
    val file = File(folder, name.lowercase())
    client.get(Url(url)).bodyAsChannel().copyAndClose(file.writeChannel())
}

class ChangeName(val newName: (name: String?) -> String) : Rename() {
    override fun apply(name: String?, param: ThumbnailParameter?): String {
        return newName(name)
    }
}
