package eu.codlab.lorcana.app.views.session.opened.page.principal.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.germainkevin.collapsingtopbar.CollapsingTopBar
import com.germainkevin.collapsingtopbar.rememberCollapsingTopBarScrollBehavior
import com.github.codlab.lorcana.card.Card
import com.willowtreeapps.fuzzywuzzy.diffutils.FuzzySearch
import eu.codlab.lorcana.app.theme.LocalDarkTheme
import eu.codlab.lorcana.app.theme.LocalThemeEnvironment
import eu.codlab.lorcana.app.theme.LorcanaIcons
import eu.codlab.lorcana.app.theme.MyApplicationTheme
import eu.codlab.lorcana.app.theme.gradient
import eu.codlab.lorcana.app.theme.lorcanaicons.Inkpot
import eu.codlab.lorcana.app.views.home.LocalApp
import eu.codlab.lorcana.app.views.session.opened.page.principal.cards.views.CardItem
import eu.codlab.lorcana.app.views.widgets.LorcanaOutlinedEditText
import eu.codlab.lorcana.app.views.widgets.StatusBarAndNavigation
import eu.codlab.lorcana.app.views.widgets.TextNormal
import eu.codlab.lorcana.app.views.widgets.TextTitle

internal class CardsList(val onCard: (Card) -> Unit) : Tab {

    private val minGridCellSize = 128.dp

    override val options: TabOptions
        @Composable
        get() {
            val title = "Cards"
            val icon = rememberVectorPainter(LorcanaIcons.Inkpot)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun Content() {
        StatusBarAndNavigation()

        val states by LocalApp.current.states.collectAsState()
        val search = remember { mutableStateOf(TextFieldValue("")) }
        var cards by remember { mutableStateOf(states.cards) }
        val showCollection = remember { mutableStateOf(false) }

        LaunchedEffect(search.value) {
            cards = searchCards(states.cardMaps, search.value.text) ?: states.cards
        }

        val minHeight = 112.dp
        val maxHeight = 200.dp

        val lazyGridState = rememberLazyGridState()
        val themeEnvironment = LocalThemeEnvironment.current
        val scrollBehavior = rememberCollapsingTopBarScrollBehavior(
            isAlwaysCollapsed = false,
            availableYScaleFactor = 5,
            collapsedTopBarHeight = minHeight,
            expandedTopBarMaxHeight = maxHeight
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .gradient(
                    themeEnvironment.gradientStart,
                    themeEnvironment.gradientEnd
                )
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            CollapsingTopBar(
                scrollBehavior = scrollBehavior
            ) { _, progress ->
                ShowHeader(
                    search = search,
                    showCollection = showCollection,
                    progress = progress
                )
            }

            LazyVerticalGrid(
                state = lazyGridState,
                columns = GridCells.Adaptive(minSize = minGridCellSize)
            ) {
                items(
                    count = cards.size,
                    key = { id ->
                        cards[id].cardNumber
                    }
                ) { photo ->
                    CardItem(cards[photo], onCard, showCollection.value)
                }
            }
        }
    }
}

@Composable
fun ShowHeader(
    search: MutableState<TextFieldValue>,
    showCollection: MutableState<Boolean>,
    progress: Float
) {
    val themeEnvironment = LocalThemeEnvironment.current
    val paddingDp = with(LocalDensity.current) {
        (12.dp.value * progress).dp
    }

    val expectedPadding = paddingDp + 4.dp

    Column(
        modifier = Modifier.padding(
            PaddingValues(
                start = themeEnvironment.defaultPadding,
                end = themeEnvironment.defaultPadding
            )
        ),
        verticalArrangement = Arrangement.spacedBy(expectedPadding)
    ) {
        Column(
            modifier = Modifier
                .padding(
                    start = expectedPadding,
                    top = expectedPadding,
                    end = expectedPadding
                )
        ) {
            @Suppress("MagicNumber")
            TextTitle(
                text = "Cards",
                fontSize = (12 + (30 - 12) * progress).sp
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ShowSearch(
                modifier = Modifier.width(180.dp)
            ) {
                search.value = it
            }

            ShowSwitchCollection {
                showCollection.value = it
            }
        }
    }
}

const val ThresholdScore = 80

fun searchCards(cards: Map<String, Card>, search: String): List<Card>? {
    if (search.isEmpty()) return null

    val found = mutableListOf<Card>()

    found += cards.values.filter {
        "${it.name} ${it.subTitle}"
            .lowercase().contains(search.lowercase())
    }

    val result = FuzzySearch.extractAll(
        search.lowercase(),
        cards.keys
    )

    val treshold = result.filter { it.score > ThresholdScore }

    found += treshold.map { cards[it.string] }.filter { card ->
        null == found.find { null != card && it.cardNumber == card.cardNumber }
    }.filterNotNull()

    return found
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ShowSearch(
    modifier: Modifier = Modifier,
    onSearch: (search: TextFieldValue) -> Unit
) {
    var search by remember { mutableStateOf(TextFieldValue("")) }

    val newColor = if (LocalDarkTheme.current) {
        Color.White
    } else {
        Color.Black
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    LorcanaOutlinedEditText(
        modifier = modifier,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = newColor
        ),
        label = {
            TextNormal(text = "Search", color = newColor)
        },
        leadingIcon = {
            Image(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = newColor)
            )
        },
        value = search,
        onValueChanged = {
            search = it
            onSearch(it)
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
    )
}

const val Opaque = 1f
const val SemiTransparent = 0.6f

@Composable
fun ShowSwitchCollection(
    modifier: Modifier = Modifier,
    showCollection: (Boolean) -> Unit
) {
    val defaultPadding = LocalThemeEnvironment.current.defaultPadding

    var selected by remember { mutableStateOf(false) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Column(
            modifier = Modifier.alpha(
                if (selected) {
                    SemiTransparent
                } else {
                    Opaque
                }
            )
        ) {
            TextNormal(
                fontSize = 12.sp,
                text = "All",
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.width(defaultPadding))
        Switch(
            checked = selected,
            onCheckedChange = {
                selected = it
                showCollection(it)
            }
        )
        Spacer(modifier = Modifier.width(defaultPadding))
        Column(
            modifier = Modifier.alpha(
                if (selected) {
                    Opaque
                } else {
                    SemiTransparent
                }
            )
        ) {
            TextNormal(
                fontSize = 12.sp,
                text = "Collection",
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(widthDp = 400, heightDp = 500)
@Composable
fun CardsListPreview() {
    MyApplicationTheme(darkTheme = false) {
        Column(Modifier.background(Color.White)) {
            CardsList {}.Content()
        }
    }
}

@Preview(widthDp = 600, heightDp = 500)
@Composable
fun CardsListPreviewExpanded() {
    MyApplicationTheme(darkTheme = false) {
        Column(Modifier.background(Color.White)) {
            CardsList {}.Content()
        }
    }
}
