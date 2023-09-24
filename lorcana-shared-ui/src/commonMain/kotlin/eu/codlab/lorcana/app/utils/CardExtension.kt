package eu.codlab.lorcana.app.utils

import com.github.codlab.lorcana.card.Card
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.getImageByFileName
import eu.codlab.lorcana.resources.Resources

fun Card.getImage(mode: String, size: String): ImageResource {
    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    return try {
        Resources.images.getImageByFileName(
            "${this.setCode}_${mode}_${size}_${this.cardNumber}".lowercase()
        ) ?: Resources.images.cardBackx300
    } catch (err: Throwable) {
        Resources.images.cardBackx300
    }
}
