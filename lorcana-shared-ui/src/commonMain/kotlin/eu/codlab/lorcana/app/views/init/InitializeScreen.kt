package eu.codlab.lorcana.app.views.init

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import eu.codlab.lorcana.app.theme.AppColor
import eu.codlab.lorcana.app.theme.MyApplicationTheme
import eu.codlab.lorcana.app.views.home.AppModel
import eu.codlab.lorcana.app.views.home.GlobalApp
import eu.codlab.lorcana.app.views.widgets.StatusBarAndNavigation

internal class InitializeScreen : Screen {
    @Composable
    override fun Content() {
        InitializeScreenDisplay(
            GlobalApp,
            Modifier.fillMaxSize()
        )
    }
}

@Composable
fun InitializeScreenDisplay(
    globalApp: AppModel,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        if (!globalApp.isInitialized()) {
            globalApp.initialize()
        }
    }

    StatusBarAndNavigation()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppColor.BackgroundBlue)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .clip(
                    RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp,
                        bottomStart = 10.dp,
                        bottomEnd = 10.dp
                    )
                )
                .shadow(
                    elevation = 10.dp,
                    clip = true
                )
                .heightIn(0.dp, 400.dp) // mention max height here
                .widthIn(0.dp, 800.dp) // mention max width here
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun InitializeScreenPreviewDark() {
    MyApplicationTheme(darkTheme = true) {
        InitializeScreenDisplay(
            globalApp = AppModel(),
            modifier = Modifier
                .width(600.dp)
                .height(300.dp)
        )
    }
}

@Preview
@Composable
fun InitializeScreenPreviewLight() {
    MyApplicationTheme(darkTheme = false) {
        InitializeScreenDisplay(
            globalApp = AppModel(),
            modifier = Modifier
                .width(600.dp)
                .height(300.dp)
        )
    }
}
