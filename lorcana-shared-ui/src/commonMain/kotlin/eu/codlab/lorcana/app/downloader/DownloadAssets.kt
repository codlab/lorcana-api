package eu.codlab.lorcana.downloader

import com.github.codlab.lorcana.card.Card
import com.github.codlab.lorcana.files.VirtualFile
import com.github.codlab.lorcana.files.touch
import com.github.codlab.lorcana.http.createClient
import com.github.codlab.lorcana.lorcania.LorcanaCard
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.http.Url

class DownloadAssets {

    val client = createClient()

    private val download = VirtualFile(VirtualFile.Root, "downloads")

    suspend fun start() {
        println("creating ${download.absolutePath}")
        if (!download.exists()) {
            println("creating ${download.absolutePath}")
            download.mkdirs()
        }
    }

    suspend fun exists(
        card: Card,
        lang: String,
        mode: String = "normal",
        size: String = "large"
    ): Boolean {
        val local = card.getLocalUrl(
            mode = mode,
            size = size,
            lang
        )
        val localPath = VirtualFile(download, local)

        return localPath.exists()
    }

    fun local(
        card: LorcanaCard,
        lang: String,
        mode: String = "normal",
        size: String = "large"
    ): VirtualFile {
        val local = card.getLocalUrl(
            mode = mode,
            size = size,
            lang = lang
        )

        return VirtualFile(download, "$local.webp")
    }

    fun remote(
        card: LorcanaCard,
        lang: String,
        mode: String = "normal",
        size: String = "large"
    ): String {
        return card.getRemoteUrl(
            mode = mode,
            size = size,
            lang = lang
        )
    }

    suspend fun fetch(
        card: LorcanaCard,
        lang: String,
        mode: String = "normal",
        size: String = "large"
    ) {
        val remote = card.getRemoteUrl(
            mode = mode,
            size = size,
            lang = lang
        ).trim()

        val local = card.getLocalUrl(
            mode = "normal",
            size = "large",
            lang
        )

        println("attempt for $remote is $local")

        var extension = remote.split(".").last()

        println("extension $extension")

        if (extension == remote) {
            println("$extension and $remote")
            extension = ""
        } else {
            extension = ".$extension"
        }
        println("extension $extension")

        val localPath = VirtualFile(download, "$local$extension")

        if (!localPath.exists()) {
            println("${localPath.absolutePath} does not exists")
            val httpResponse = client.get(Url(remote))
            if (httpResponse.status == HttpStatusCode.Accepted) {
                touch(localPath.absolutePath)
                localPath.write(httpResponse.body<ByteArray>())
            }
            // client.get(Url(remote)).bodyAsChannel().copyAndClose(file.writeChannel())
        } else {
            println("${localPath.absolutePath} does exist")
        }
    }
}
