package eu.codlab.lorcana.app.utils

import com.github.codlab.lorcana.card.Card
import com.github.codlab.lorcana.sharedui.Resources
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.getImageByFileName


fun Card.getImage(mode: String, size: String): ImageResource {
    return try {
        Resources.images.getImageByFileName(
            "${this.setCode}_${mode}_${size}_${this.cardNumber}".lowercase()
        ) ?: Resources.images.cardBackx300
    } catch (err: Throwable) {
        Resources.images.cardBackx300
    }
}