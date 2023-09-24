package eu.codlab.lorcana.app.views.menu

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.SuppressLint
import androidx.compose.ui.unit.dp
import eu.codlab.lorcana.app.theme.MyApplicationTheme
import eu.codlab.lorcana.app.theme.WindowSize
import eu.codlab.lorcana.app.views.home.LocalWindow
import eu.codlab.lorcana.app.views.widgets.SafeArea
import eu.codlab.lorcana.app.views.widgets.SafeAreaBehavior
import eu.codlab.lorcana.app.views.widgets.TransparentIconButton
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Suppress("TooGenericExceptionCaught")
@Composable
fun MenuWrapperContent(content: @Composable () -> Unit) {
    val isScreenExpanded = LocalWindow.current == WindowSize.EXPANDED

    val scaffoldState = rememberSizeAwareScaffoldState()

    SafeArea(
        behavior = remember {
            SafeAreaBehavior(
                extendToBottom = true
            )
        }
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            drawerContent = {
                if (!isScreenExpanded) {
                    DrawerContent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                println("having drawer content to display ? $isScreenExpanded ${LocalWindow.current}")
                if (isScreenExpanded) {
                    DrawerContent(
                        modifier = Modifier
                            .width(250.dp)
                            .fillMaxHeight()
                    )
                } // Show permanent drawer only for large screens
                content()
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
        imageVector = Icons.Default.Menu,
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

@Preview
@Composable
fun MenuWrapperContentDisplayCompactDark() {
    MyApplicationTheme(
        darkTheme = true
    ) {
        MenuWrapperContent {}
    }
}

@Preview
@Composable
fun MenuWrapperContentDisplayCompactLight() {
    MyApplicationTheme(
        darkTheme = false
    ) {
        MenuWrapperContent {}
    }
}
