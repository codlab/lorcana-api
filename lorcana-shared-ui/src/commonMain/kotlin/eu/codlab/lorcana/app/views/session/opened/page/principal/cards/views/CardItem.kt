package eu.codlab.lorcana.app.views.session.opened.page.principal.cards.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import com.github.codlab.lorcana.card.Card
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

private const val CornerRadiusPercent = 8

@Composable
fun CardItem(card: Card, default: Painter) {
    val painterResource = asyncPainterResource(data = card.imageUrls.small)
    val shape = RoundedCornerShape(CornerRadiusPercent)
    KamelImage(
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = shape),
        resource = painterResource,
        contentDescription = card.name,
        onLoading = { _ ->
            Image(
                modifier = Modifier.clip(shape = shape),
                painter = default,
                contentDescription = ""
            )
        },
        onFailure = { _ ->
            // would be interesting to manage the state here
        }
    )
}
