package eu.codlab.lorcana.app.views.session.opened.page.principal.cards.cards.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.github.codlab.lorcana.card.Card
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource
import eu.codlab.lorcana.resources.Resources
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

private const val CornerRadiusPercent = 8
private val shape = RoundedCornerShape(CornerRadiusPercent)

@Composable
fun <Fallback> SingleCardView(
    card: Card,
    modifier: Modifier,
    model: AbstractCardModel<Fallback>,
    colorFilter: ColorFilter? = null,
    onlyLocalResource: Boolean = false
) {
    val collect = model.states.collectAsState()
    val state = collect.value

    println("having card to show ??? ${state.dataForAsync}")

    Card(
        modifier = Modifier,
        shape = shape,
        elevation = 16.dp
    ) {
        if (onlyLocalResource || state.loading || !state.loaded) {
            BackImage(
                modifier, model.imageResource(card), colorFilter
            )
        } else {
            val painterResource = asyncPainterResource(
                data = state.dataForAsync!!,
                key = "${card.setCode}_${card.cardNumber}"
            )

            KamelImage(
                modifier = modifier,
                resource = painterResource,
                contentDescription = card.name,
                colorFilter = colorFilter,
                onLoading = { _ ->
                    BackImage(
                        modifier, model.imageResource(card), colorFilter
                    )
                },
                onFailure = { _ ->
                    // would be interesting to manage the state here
                }
            )
        }
    }
}

@Composable
fun BackImage(
    modifier: Modifier,
    imageResource: ImageResource = Resources.images.cardBackx300,
    colorFilter: ColorFilter? = null
) {
    Image(
        modifier = modifier,
        painter = painterResource(imageResource),
        contentDescription = "",
        colorFilter = colorFilter
    )
}