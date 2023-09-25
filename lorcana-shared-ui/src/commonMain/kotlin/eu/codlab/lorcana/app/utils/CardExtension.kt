package eu.codlab.lorcana.app.utils

import com.github.codlab.lorcana.card.Card
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.getImageByFileName
import eu.codlab.lorcana.resources.Resources

fun Card.getImage(mode: String, size: String, lang: String = "en"): ImageResource {
    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    val attempt = "${this.setCode}_${mode}_${size}_${this.cardNumber}_${lang}".lowercase()
    println("attempt for $attempt")
    return try {
        Resources.images.getImageByFileName(attempt) ?: Resources.images.cardBackx300
    } catch (err: Throwable) {
        println("$err")
        try {
            Resources.images.getImageByFileName(
                "${this.setCode}_${mode}_${size}_${this.cardNumber}_en".lowercase()
            ) ?: Resources.images.cardBackx300
        } catch (err: Throwable) {
            Resources.images.cardBackx300
        }
    }
}

fun Card.getRemoteUrl(mode: String, size: String, lang: String = "en"): String {
    val root = "https://lorcana.codlab.eu/images/"
    return "$root/${this.setCode}_${mode}_${size}_${this.cardNumber}_${lang}@1x.webp".lowercase()
}
