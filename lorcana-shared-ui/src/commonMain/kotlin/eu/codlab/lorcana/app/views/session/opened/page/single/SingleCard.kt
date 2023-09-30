package eu.codlab.lorcana.app.views.session.opened.page.single

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.codlab.lorcana.lorcania.LorcanaCard
import eu.codlab.lorcana.app.theme.LocalDarkTheme
import eu.codlab.lorcana.app.theme.LocalThemeEnvironment
import eu.codlab.lorcana.app.theme.MyApplicationTheme
import eu.codlab.lorcana.app.utils.rememberViewModel
import eu.codlab.lorcana.app.views.home.LocalApp
import eu.codlab.lorcana.app.views.home.LocalDownloader
import eu.codlab.lorcana.app.views.session.opened.page.principal.cards.cards.views.CardItemModel
import eu.codlab.lorcana.app.views.session.opened.page.principal.cards.cards.views.SingleCardView
import eu.codlab.lorcana.app.views.widgets.LorcanaOutlinedButton
import eu.codlab.lorcana.app.views.widgets.LorcanaOutlinedEditText
import eu.codlab.lorcana.app.views.widgets.TextNormal
import eu.codlab.lorcana.app.views.widgets.TextTitle
import eu.codlab.lorcana.app.views.widgets.systemBackground
import eu.codlab.lorcana.models.FoilNormal
import eu.codlab.lorcana.resources.Resources
import eu.codlab.moko.ext.localized

@Composable
fun SingleCard(card: LorcanaCard) {
    val downloader = LocalDownloader.current
    val env = LocalThemeEnvironment.current

    val lang = Locale.current.language.split("_")[0].lowercase()
    val model = rememberViewModel { CardItemModel(downloader, lang, "normal", "large") }

    var localCard by remember { mutableStateOf(card) }

    LaunchedEffect(model) {
        // TODO manage multiple card loading ?
        model.load(card)
    }

    LaunchedEffect(card) {
        // TODO manage multiple cards ?
        localCard = card
    }

    println("having color ${env.gradientStart}")

    val color = if (LocalDarkTheme.current) {
        env.lorcanaBlue
    } else {
        Color.White
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(color),
        Arrangement.spacedBy(5.dp)
    ) {
        CardItem(card, model)
    }
}

private const val CornerRadiusPercent = 8
private val shape = RoundedCornerShape(CornerRadiusPercent)

@Composable
@Suppress("LongMethod")
fun CardItem(
    card: LorcanaCard,
    model: CardItemModel
) {
    val localApp = LocalApp.current
    val state = rememberScrollState()

    var numbers by remember {
        mutableStateOf(
            localApp.getCardNumbers(
                card.getSetId(),
                card.number.toLong()
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(
            Modifier.height(16.dp)
        )

        TextTitle(text = card.name)

        val modifier = Modifier
            .widthIn(0.dp, 400.dp)
            .heightIn(0.dp, 400.dp)
            .clip(shape = shape)
            .aspectRatio(ratio = 0.72F)

        SingleCardView(
            card = card,
            modifier = modifier,
            model = model
        )

        val update: (FoilNormal) -> Unit = {
            localApp.save(
                card.getSetId(),
                card.number.toLong(),
                it
            )
        }

        MutableIntegerBox(
            title = Resources.strings.normal.localized(),
            value = numbers.normal
        ) {
            numbers = numbers.copy(normal = if (it > 0) it else 0)
            update(numbers)
        }

        MutableIntegerBox(
            title = Resources.strings.foil.localized(),
            value = numbers.foil
        ) {
            numbers = numbers.copy(foil = it)
            update(numbers)
        }
    }
}

@Composable
fun MutableIntegerBox(
    modifier: Modifier = Modifier,
    title: String,
    value: Long,
    onNewValue: (Long) -> Unit
) {
    var actualValue by remember {
        mutableStateOf(value)
    }

    LaunchedEffect(value) {
        actualValue = value
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LorcanaOutlinedButton(onClick = {
            // prevent negative values
            if (actualValue - 1 < 0) return@LorcanaOutlinedButton
            onNewValue(actualValue - 1)
        }) {
            TextNormal(text = "-")
        }

        LorcanaOutlinedEditText(
            modifier = Modifier.widthIn(0.dp, 120.dp),
            value = TextFieldValue("$actualValue"),
            onValueChanged = {},
            readOnly = true,
            label = { TextNormal(text = title) }
        )

        LorcanaOutlinedButton(onClick = {
            onNewValue(actualValue + 1)
        }) {
            TextNormal(text = "+")
        }
    }
}

@Preview(widthDp = 300)
@Composable
fun CardPreviewDark() {
    MyApplicationTheme(darkTheme = false) {
        Column(modifier = Modifier.systemBackground()) {
            CardItem(
                card = LorcanaCard.fake(),
                model = CardItemModel.fake()
            )
        }
    }
}

@Preview(widthDp = 300)
@Composable
fun CardPreviewLight() {
    MyApplicationTheme(darkTheme = true) {
        Column(modifier = Modifier.systemBackground()) {
            CardItem(
                card = LorcanaCard.fake(),
                model = CardItemModel.fake()
            )
        }
    }
}

@Preview(widthDp = 800)
@Composable
fun CardPreviewDarkTablet() {
    MyApplicationTheme(darkTheme = false) {
        SingleCard(card = LorcanaCard.fake())
    }
}

@Preview(widthDp = 800)
@Composable
fun CardPreviewLightTablet() {
    MyApplicationTheme(darkTheme = true) {
        SingleCard(card = LorcanaCard.fake())
    }
}
