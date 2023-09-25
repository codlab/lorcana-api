package eu.codlab.lorcana.app.utils

import com.github.codlab.lorcana.card.Card
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.getImageByFileName
import eu.codlab.lorcana.resources.Resources

fun Card.getImage(mode: String, size: String, lang: String = "en"): ImageResource {
    val attempt = getLocalUrl(mode, size, lang)

    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    return try {
        Resources.images.getImageByFileName(attempt) ?: Resources.images.cardBackx300
    } catch (err: Throwable) {
        try {
            val en = getLocalUrl(mode, size, "en")
            Resources.images.getImageByFileName(en) ?: Resources.images.cardBackx300
        } catch (err: Throwable) {
            Resources.images.cardBackx300
        }
    }
}

fun Card.getRemoteUrl(mode: String, size: String, lang: String = "en"): String {
    val root = "https://lorcana.codlab.eu/images/"
    return "$root/${this.setCode}_${mode}_${size}_${this.cardNumber}_$lang@1x.webp".lowercase()
}

fun Card.getLocalUrl(mode: String, size: String, lang: String = "en"): String {
    return "${this.setCode}_${mode}_${size}_${this.cardNumber}_$lang".lowercase()
}
