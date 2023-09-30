package eu.codlab.lorcana.app.views.session.opened.page.principal.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
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
import com.germainkevin.collapsingtopbar.CollapsingTopBar
import com.germainkevin.collapsingtopbar.rememberCollapsingTopBarScrollBehavior
import com.github.codlab.lorcana.lorcania.LorcanaCard
import com.github.codlab.lorcana.lorcania.LorcanaSet
import com.willowtreeapps.fuzzywuzzy.diffutils.FuzzySearch
import eu.codlab.lorcana.app.theme.AppColor
import eu.codlab.lorcana.app.theme.LocalDarkTheme
import eu.codlab.lorcana.app.theme.LocalThemeEnvironment
import eu.codlab.lorcana.app.theme.LorcanaIcons
import eu.codlab.lorcana.app.theme.MyApplicationTheme
import eu.codlab.lorcana.app.theme.lorcanaicons.Inkpot
import eu.codlab.lorcana.app.views.home.LocalApp
import eu.codlab.lorcana.app.views.session.opened.page.principal.cards.cards.menu.MenuSets
import eu.codlab.lorcana.app.views.session.opened.page.principal.cards.cards.views.CardItem
import eu.codlab.lorcana.app.views.widgets.LorcanaOutlinedEditText
import eu.codlab.lorcana.app.views.widgets.StatusBarAndNavigation
import eu.codlab.lorcana.app.views.widgets.TextNormal
import eu.codlab.lorcana.app.views.widgets.TextTitle
import eu.codlab.lorcana.app.views.widgets.TopSpacer
import eu.codlab.lorcana.resources.Resources
import eu.codlab.moko.ext.localized

private val minGridCellSize = 128.dp

internal class CardsList(val onCard: (LorcanaCard) -> Unit) : Tab {
    override val option: TabOptions
        @Composable
        get() {
            val title = Resources.strings.cards.localized()
            val icon = rememberVectorPainter(LorcanaIcons.Inkpot)

            return remember {
                TabOptions(
                    index = 1,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val useCornerTop = LocalDarkTheme.current

        MyApplicationTheme(darkTheme = true) {
            ShowCardList(
                onCard = onCard,
                useCornerTop = useCornerTop
            )
        }
    }
}

@Suppress("LongMethod")
@Composable
fun ShowCardList(
    onCard: (LorcanaCard) -> Unit,
    useCornerTop: Boolean
) {
    StatusBarAndNavigation()
    val states by LocalApp.current.states.collectAsState()
    val search = remember { mutableStateOf(TextFieldValue("")) }
    val showCollection = remember { mutableStateOf(false) }

    var set by remember { mutableStateOf(states.cardSets.firstOrNull()) }

    var cards = set?.cards() ?: emptyList()

    println("set $set")

    LaunchedEffect(set, search.value) {
        val actualset = set ?: return@LaunchedEffect

        cards = searchCards(actualset, states.lang, search.value.text) ?: actualset.cards()
    }

    val minHeight = 112.dp
    val maxHeight = 200.dp

    val lazyGridState = rememberLazyGridState()
    val scrollBehavior = rememberCollapsingTopBarScrollBehavior(
        isAlwaysCollapsed = false,
        availableYScaleFactor = 5,
        collapsedTopBarHeight = minHeight,
        expandedTopBarMaxHeight = maxHeight
    )
    var currentProgress by remember {
        mutableFloatStateOf(1f)
    }
    val cornerDp = with(LocalDensity.current) {
        5.dp + (20.dp.value * currentProgress).dp
    }

    val modifier = if (useCornerTop) {
        Modifier
            .clip(
                shape = RoundedCornerShape(
                    topStart = cornerDp,
                    topEnd = cornerDp
                )
            )
            .fillMaxSize()
    } else {
        Modifier.fillMaxSize()
    }

    Column(
        modifier = modifier
            .background(color = AppColor.LorcanaDarkBlue)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        TopSpacer()

        CollapsingTopBar(
            scrollBehavior = scrollBehavior
        ) { _, progress ->
            currentProgress = progress
            ShowHeader(
                search = search,
                showCollection = showCollection,
                progress = progress,
                set = set
            ) {
                set = it
            }
        }

        LazyVerticalGrid(
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        topStart = cornerDp,
                        topEnd = cornerDp
                    )
                )
                .background(
                    color = if (LocalDarkTheme.current) {
                        Color.LightGray
                    } else {
                        Color.White
                    }
                ),
            state = lazyGridState,
            columns = GridCells.Adaptive(minSize = minGridCellSize)
        ) {
            items(
                count = cards.size,
                key = { id ->
                    cards[id].number
                }
            ) { photo ->
                CardItem(cards[photo], showCollection.value, onCard)
            }
        }
    }
}

@Composable
fun ShowHeader(
    search: MutableState<TextFieldValue>,
    showCollection: MutableState<Boolean>,
    progress: Float,
    set: LorcanaSet? = null,
    onSet: (LorcanaSet) -> Unit
) {
    val themeEnvironment = LocalThemeEnvironment.current
    val paddingDp = with(LocalDensity.current) {
        (12.dp.value * progress).dp
    }

    val expectedPadding = paddingDp + 4.dp

    val states by LocalApp.current.states.collectAsState()

    Box(modifier = Modifier.fillMaxWidth()) {
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
                    text = set?.name() ?: Resources.strings.cards.localized(),
                    fontSize = (12 + (30 - 12) * progress).sp
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ShowSearch(
                    modifier = Modifier.width(160.dp)
                ) {
                    search.value = it
                }

                ShowSwitchCollection {
                    showCollection.value = it
                }
            }
        }

        MenuSets(
            modifier = Modifier.align(
                alignment = Alignment.TopEnd
            ),
            sets = states.cardSets,
            onSet = onSet
        )
    }
}

const val ThresholdScore = 80

fun searchCards(set: LorcanaSet, lang: String, search: String): List<LorcanaCard>? {
    val translations = set.translations(lang)
    if (null == translations || search.isEmpty()) return null

    val found = mutableListOf<LorcanaCard>()

    found += set.cards().filter {
        "${it.name} ${it.title}"
            .lowercase().contains(search.lowercase())
    }

    val result = FuzzySearch.extractAll(
        search.lowercase(),
        translations.keys()
    )

    val treshold = result.filter { it.score > ThresholdScore }

    return found + treshold.map { translations.get(it.string ?: "") }.filter { card ->
        null == found.find { null != card && it.number == card.number }
    }.filterNotNull()
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
            TextNormal(
                text = Resources.strings.search.localized(),
                color = newColor,
                fontSize = 12.sp
            )
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
                text = Resources.strings.all.localized(),
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
                text = Resources.strings.collection.localized(),
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
