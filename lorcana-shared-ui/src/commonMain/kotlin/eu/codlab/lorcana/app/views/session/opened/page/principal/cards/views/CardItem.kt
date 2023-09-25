package eu.codlab.lorcana.app.views.session.opened.page.principal.cards.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.unit.dp
import com.github.codlab.lorcana.card.Card
import dev.icerock.moko.resources.compose.painterResource
import eu.codlab.lorcana.app.utils.getImage
import eu.codlab.lorcana.app.views.home.LocalApp
import eu.codlab.lorcana.models.FoilNormal

private const val CornerRadiusPercent = 8
private val shape = RoundedCornerShape(CornerRadiusPercent)

private val GridCellPadding = 10.dp
private const val Ratio = 0.75f

@Composable
fun CardItem(card: Card, onCard: (Card) -> Unit, showCollection: Boolean = true) {
    val localApp = LocalApp.current

    var image by remember { mutableStateOf(card.getImage("normal", "small")) }
    var numbers by remember { mutableStateOf(FoilNormal(0, 0)) }

    LaunchedEffect(card, showCollection) {
        numbers = localApp.getCardNumbers(card.setCode, card.cardNumber.toLong())
    }

    LaunchedEffect(card) {
        image = card.getImage("normal", "small")
    }

    println("having image $image")

    Box(
        modifier = Modifier
            .aspectRatio(Ratio)
            .fillMaxWidth()
            .padding(GridCellPadding)
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(shape)
                .clickable { onCard(card) },
            colorFilter = if (!showCollection || numbers.isOwned()) {
                null
            } else {
                ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
            }
        )
    }
    /*
            val painterResource = asyncPainterResource(data = image ?: card.imageUrls.small)
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
     */
}
