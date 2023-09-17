package eu.codlab.lorcana.app.views.session.opened

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.SuppressLint
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import eu.codlab.lorcana.app.theme.MyApplicationTheme
import eu.codlab.lorcana.app.theme.WindowSize
import eu.codlab.lorcana.app.utils.FontIcon
import eu.codlab.lorcana.app.views.home.AppModel
import eu.codlab.lorcana.app.views.home.LocalWindow
import eu.codlab.lorcana.app.views.session.opened.menu.DrawerContent
import eu.codlab.lorcana.app.views.session.opened.menu.DrawerSizeShape
import eu.codlab.lorcana.app.views.session.opened.page.licenses.LicensesScreen
import eu.codlab.lorcana.app.views.widgets.TransparentIconButton
import korlibs.io.async.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Suppress("TooGenericExceptionCaught")
@Composable
fun OpenedSessionContent(appModel: AppModel) {
    val isScreenExpanded = LocalWindow.current == WindowSize.EXPANDED

    val scaffoldState = rememberSizeAwareScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            if (!isScreenExpanded) {
                DrawerContent(
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        drawerGesturesEnabled = !isScreenExpanded, // Gestures are enabled only on smaller and medium screens
        drawerShape = DrawerSizeShape(),
        topBar = {
            if (!isScreenExpanded) {
                ToggleDrawerButton(scaffoldState.drawerState)
            }
        }
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            println("having drawer content to display ? $isScreenExpanded ${LocalWindow.current}")
            if (isScreenExpanded) {
                DrawerContent(
                    modifier = Modifier.width(250.dp)
                )
            } // Show permanent drawer only for large screens
            Navigator(LicensesScreen()) {
                CurrentScreen()
            }
        }
    }
}


@Composable
private fun rememberSizeAwareScaffoldState(): ScaffoldState {
    val commonSnackbarHostState = remember { SnackbarHostState() }
    val compactScaffoldState = rememberScaffoldState(
        drawerState = rememberDrawerState(DrawerValue.Closed),
        snackbarHostState = commonSnackbarHostState
    )
    val expandedScaffoldState = rememberScaffoldState(
        drawerState = DrawerState(DrawerValue.Closed),
        snackbarHostState = commonSnackbarHostState
    )
    val isScreenExpanded = LocalWindow.current == WindowSize.EXPANDED
    println("rememberSizeAwareScaffoldState isScreenExpanded/$isScreenExpanded")
    return if (isScreenExpanded) {
        expandedScaffoldState
    } else {
        compactScaffoldState
    }
}

@Composable
fun ToggleDrawerButton(drawerState: DrawerState) {
    val coroutineScope = rememberCoroutineScope()
    TransparentIconButton(
        modifier = Modifier
            .width(32.dp)
            .height(32.dp),
        fontFamily = FontIcon.fontFamily(),
        fontSize = with(LocalDensity.current) {
            32.dp.toSp()
        },
        text = FontIcon.menu,
        onClick = {
            coroutineScope.launch {
                if (drawerState.isOpen) {
                    println("open")
                    drawerState.close()
                } else {
                    println("close")
                    drawerState.open()
                }
            }
        }
    )
}

@Preview(widthDp = 720)
@Composable
fun OpenedSessionContentDisplayLight() {
    MyApplicationTheme(
        darkTheme = false
    ) {
        OpenedSessionContent(
            appModel = AppModel.fake()
        )
    }
}

@Preview
@Composable
fun OpenedSessionContentDisplayCompactDark() {
    MyApplicationTheme(
        darkTheme = true
    ) {
        OpenedSessionContent(
            appModel = AppModel.fake()
        )
    }
}


@Preview
@Composable
fun OpenedSessionContentDisplayCompactLight() {
    MyApplicationTheme(
        darkTheme = false
    ) {
        OpenedSessionContent(
            appModel = AppModel.fake()
        )
    }
}

@Preview(widthDp = 720)
@Composable
fun OpenedSessionContentDisplayDark() {
    MyApplicationTheme(
        darkTheme = true
    ) {
        OpenedSessionContent(
            appModel = AppModel.fake()
        )
    }
}
