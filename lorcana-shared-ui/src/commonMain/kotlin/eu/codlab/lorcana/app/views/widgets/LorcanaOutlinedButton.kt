package eu.codlab.lorcana.app.views.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import eu.codlab.lorcana.app.theme.MyApplicationTheme

@Suppress("LongParameterList")
@Composable
fun LorcanaOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = null,
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = BorderStroke(
        ButtonDefaults.OutlinedBorderSize,
        MaterialTheme.colors.primary
    ),
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.onSurface),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) = OutlinedButton(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource,
    elevation = elevation,
    shape = shape,
    border = border,
    colors = colors,
    contentPadding = contentPadding,
    content = content
)


@Preview(widthDp = 250)
@Composable
private fun LorcanaOutLinedButtonPreviewDark() =
    MyApplicationTheme(darkTheme = true) {
        LorcanaOutlinedButton(
            onClick = { },
            content = {
                TextNormal(text = "button ")
            }
        )
    }

@Preview(widthDp = 250)
@Composable
private fun LorcanaOutLinedButtonPreviewLight() =
    MyApplicationTheme(darkTheme = false) {
        LorcanaOutlinedButton(
            onClick = { },
            content = {
                TextNormal(text = "button ")
            }
        )
    }
