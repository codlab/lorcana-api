package eu.codlab.lorcana.app.views.session.opened.page.principal.cards.cards.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.codlab.lorcana.Platforms
import com.github.codlab.lorcana.card.Card
import com.github.codlab.lorcana.currentPlatform
import eu.codlab.lorcana.app.theme.MyApplicationTheme
import eu.codlab.lorcana.app.utils.rememberViewModel
import eu.codlab.lorcana.app.views.home.LocalApp
import eu.codlab.lorcana.app.views.home.LocalDownloader
import eu.codlab.lorcana.models.FoilNormal

private const val CornerRadiusPercent = 8
private val shape = RoundedCornerShape(CornerRadiusPercent)

private val GridCellPadding = 10.dp
private const val Ratio = 0.75f
private const val CardAspectRatio = 0.70f

@Composable
fun CardItem(card: Card, showCollection: Boolean = true, onCard: (Card) -> Unit) {
    val localApp = LocalApp.current

    var numbers by remember { mutableStateOf(FoilNormal(0, 0)) }

    LaunchedEffect(card, showCollection) {
        numbers = localApp.getCardNumbers(card.setCode, card.cardNumber.toLong())
    }

    val downloader = LocalDownloader.current

    val lang = Locale.current.language.split("_")[0].lowercase()
    val model = rememberViewModel { CardItemModel(downloader, lang, "normal", "large") }

    LaunchedEffect(card) {
        model.load(card)
    }

    Box(
        modifier = Modifier
            .aspectRatio(Ratio)
            .fillMaxWidth()
            .padding(GridCellPadding)
    ) {
        SingleCardView(
            card = card,
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
                .clip(shape)
                .clickable { onCard(card) }
                .clipToBounds()
                .aspectRatio(CardAspectRatio),
            model = model,
            colorFilter = if (showCollection && !numbers.isOwned()) {
                ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
            } else {
                null
            },
            onlyLocalResource = currentPlatform() == Platforms.IOS
        )
    }
}

@Preview
@Composable
fun CardItemPreview() {
    MyApplicationTheme {
        Column(modifier = Modifier.background(Color.White)) {
            CardItem(card = Card.fake()) {}
        }
    }
}
