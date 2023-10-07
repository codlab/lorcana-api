package eu.codlab.lorcana.app.views.session.opened.page.licenses

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.codlab.compose.widgets.systemBackground
import eu.codlab.lorcana.app.theme.MyApplicationTheme
import eu.codlab.lorcana.app.utils.rememberViewModel

@Suppress("TooGenericExceptionCaught")
@Composable
fun LicensesContent(
    model: LicensesModel = rememberViewModel { LicensesModel() }
) {
    val state by model.states.collectAsState()

    LaunchedEffect(state.loaded) {
        if (!state.loaded) {
            model.load()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBackground()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (!state.loaded) {
                CircularProgressIndicator()
            } else {
                LazyColumn(
                    modifier = Modifier
                ) {
                    items(state.licenses.size) {
                        LicenseItem(state.licenses[it])

                        Divider(
                            modifier = Modifier.height(16.dp),
                            color = Color.Transparent
                        )
                    }
                }
            }
        }
    }
}

private val fakeModel = LicensesModel.fake(
    loaded = true,
    licenses = listOf(
        fakeLicense,
        fakeLicense,
        fakeLicense,
        fakeLicense
    )
)

@Preview
@Composable
fun LicensesContentPreviewLight() {
    MyApplicationTheme(darkTheme = false) {
        LicensesContent(
            fakeModel
        )
    }
}

@Preview
@Composable
fun LicensesContentPreviewDark() {
    MyApplicationTheme(darkTheme = true) {
        LicensesContent(
            fakeModel
        )
    }
}
