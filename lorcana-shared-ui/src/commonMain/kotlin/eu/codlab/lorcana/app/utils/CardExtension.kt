package eu.codlab.lorcana.app.utils

import com.github.codlab.lorcana.card.Card
import com.github.codlab.lorcana.lorcania.LorcanaCard
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

fun LorcanaCard.getImage(mode: String, size: String, lang: String = "en"): ImageResource {
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

fun LorcanaCard.hasLocalResource(
    lang: String,
    mode: String = "normal",
    size: String = "small"
): Boolean {
    val attempt = getLocalUrl(mode, size, lang)

    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    return try {
        null != Resources.images.getImageByFileName(attempt)
    } catch (exception: Throwable) {
        false
    }
}
