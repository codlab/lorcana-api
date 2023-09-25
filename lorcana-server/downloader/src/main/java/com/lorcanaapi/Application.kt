package com.lorcanaapi

import com.github.codlab.lorcana.card.Card
import com.github.codlab.lorcana.files.readContent
import com.github.codlab.lorcana.lorcania.LorcaniaHolder
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
import net.coobird.thumbnailator.name.Rename
import java.io.File

var cards: List<Card> = emptyList()

val client = HttpClient(CIO) {
}

// for foiled cards
// private val ExpectedSizeImage = Pair(186, 260)

@Suppress("LongMethod")
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

    // manage lorcania cards !
    listOf(
        Pair("d23", SharedRes.files.d23),
        Pair("tfc", SharedRes.files.tfc),
        Pair("rotf", SharedRes.files.rotf)
    ).forEach {
        runBlocking {
            val setCode = it.first
            val content = it.second.readContent()
            val mapped = LorcaniaHolder.fromContent(content)
            println(content.length)

            val cards = mapped.props.cards.values

            cards.forEach { card ->
                val fr = card.translation("FR")
                val de = card.translation("FR")
                val validMain = !card.image().contains("cards%2Fback")

                listOfNotNull(
                    Pair("en", card.image()),
                    if (null != fr && validMain) Pair("fr", fr.image()) else null,
                    if (null != de && validMain) Pair("de", de.image()) else null
                ).forEach { langUrl ->
                    val url = langUrl.second
                    val lang = langUrl.first

                    val folder = "src/data/lorcania_images"
                    val filenameLarge = "${setCode}_normal_large_${card.number}_$lang@1x.webp"
                    val filenameSmall = "${setCode}_normal_small_${card.number}_$lang@1x.webp"
                    val expectedFolder = File(rootDir, folder)
                    val expectedLargeFile = File(expectedFolder, filenameLarge)
                    val expectedFile = File(expectedFolder, filenameSmall)

                    val valid = url.isNotEmpty() && !url.contains("cards%2Fback")

                    if (!expectedLargeFile.exists() && valid) {
                        downloadImage(
                            url,
                            rootDir,
                            filenameLarge,
                            folder
                        )
                    }
                    if (!expectedFile.exists() && valid) {
                        downloadImage(
                            url,
                            rootDir,
                            filenameSmall,
                            folder
                        )
                    }
                }
            }
        }
    }

    // when reinstating the foil cards, if necessary :
    /*
    runBlocking {
        cards.forEach { card ->
            println("doing ${card.setCode}/${card.cardNumber}")
            val imagesFolder = File(rootDir, "src/data/images")

            listOf(imagesFolder).forEach {
                if (!it.exists()) it.mkdirs()
            }

            val urls = card.imageUrls

            //listOf(
            //    // not downloading the foil, large & medium due to oversize
            //    // Pair(urls.foil, "foil"),
            //    // Pair(urls.large, "large"),
            //    // Pair(urls.medium, "medium"),
            //    Pair(urls.small, "${card.setCode}_normal_small_${card.cardNumber}@1x.png")
            //).forEach {
            //    downloadImage(it.first, rootDir, it.second)
            //}

            val folder = "src/data/images"
            val filename = "${card.setCode}_foil_large_${card.cardNumber}_en@1x.png".lowercase()
            val thumbnailed = "${card.setCode}_foil_small_${card.cardNumber}_en@1x.png".lowercase()
            val expectedFolder = File(rootDir, folder)
            val expectedFile = File(expectedFolder, thumbnailed)

            if (!expectedFile.exists() && urls.foil.isNotEmpty()) {
                println("download to ${expectedFile.absolutePath}")
                downloadImage(
                    urls.foil,
                    rootDir,
                    filename,
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
    */
}

@Suppress("TooGenericExceptionCaught", "SwallowedException")
suspend fun downloadImage(
    url: String,
    root: String,
    name: String,
    path: String = "src/data/images"
): Boolean {
    return try {
        val folder = File(root, path)
        if (!folder.exists()) folder.mkdirs()
        val file = File(folder, name.lowercase())
        client.get(Url(url)).bodyAsChannel().copyAndClose(file.writeChannel())
        true
    } catch (e: Throwable) {
        false
    }
}

class ChangeName(val newName: (name: String?) -> String) : Rename() {
    override fun apply(name: String?, param: ThumbnailParameter?): String {
        return newName(name)
    }
}
