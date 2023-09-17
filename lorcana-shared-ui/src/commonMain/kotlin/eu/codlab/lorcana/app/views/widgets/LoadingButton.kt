package eu.codlab.lorcana.app.views.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.codlab.lorcana.app.theme.MyApplicationTheme

@Composable
fun LoadingButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier,
        enabled = !isLoading,
        onClick = onClick
    ) {
        LoadingButtonContent(isLoading, content)
    }
}

@Composable
fun OutlinedLoadingButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit = {}
) {
    LorcanaOutlinedButton(
        modifier = modifier,
        enabled = !isLoading && enabled,
        onClick = onClick
    ) {
        LoadingButtonContent(isLoading, content)
    }
}

@Composable
private fun RowScope.LoadingButtonContent(
    isLoading: Boolean,
    content: @Composable (RowScope.() -> Unit)
) {
    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.size(25.dp))
    } else {
        content()
    }
}

@Composable
private fun LoadingButtonPreview() {
    val modifier = Modifier.fillMaxWidth()
    Column {
        LoadingButton(modifier = modifier, isLoading = true, onClick = {}, content = { Text("Loading button") })
        LoadingButton(modifier = modifier, isLoading = false, onClick = {}, content = { Text("Loading button") })

        OutlinedLoadingButton(modifier = modifier, isLoading = true, onClick = {}, content = { Text("Outlined loading button") })
        OutlinedLoadingButton(modifier = modifier, isLoading = false, onClick = {}, content = { Text("Outlined loading button") })
    }
}

@Preview(widthDp = 250)
@Composable
private fun LoadingButtonPreviewDark() = MyApplicationTheme(darkTheme = true) { LoadingButtonPreview() }

@Preview(widthDp = 250)
@Composable
private fun LoadingButtonPreviewLight() = MyApplicationTheme(darkTheme = false) { LoadingButtonPreview() }
