package eu.codlab.lorcana.app.utils

import com.github.codlab.lorcana.card.LorcanaCard
import dev.icerock.moko.resources.ImageResource
import eu.codlab.lorcana.resources.Resources
import eu.codlab.moko.ext.getImageByFileNameExt

fun LorcanaCard.getImage(
    mode: String,
    size: String,
    lang: String = "en",
    fallback: Boolean = true
): ImageResource {
    val attempt = getLocalUrl(mode, size, lang)

    println("attempt for the image is $attempt")

    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    return try {
        Resources.images.getImageByFileNameExt(attempt)
            ?: if (fallback) {
                getImage(mode, size, "en", false)
            } else {
                Resources.images.cardBackx300
            }
    } catch (err: Throwable) {
        try {
            val en = getLocalUrl(mode, size, "en")
            Resources.images.getImageByFileNameExt(en) ?: Resources.images.cardBackx300
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
        null != Resources.images.getImageByFileNameExt(attempt)
    } catch (exception: Throwable) {
        false
    }
}
