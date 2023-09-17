package eu.codlab.lorcana.app.views.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import eu.codlab.lorcana.app.theme.MyApplicationTheme

@Composable
fun LorcanaOutlinedEditText(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (value: String) -> Unit,
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = BorderStroke(
        ButtonDefaults.OutlinedBorderSize,
        MaterialTheme.colors.primary
    ),
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle.Default,
    label: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = MaterialTheme.colors.onSurface
    )
) = LorcanaOutlinedEditText(
    modifier = modifier,
    value = TextFieldValue(value),
    onValueChanged = { value -> onValueChanged(value.text) },
    shape = shape,
    border = border,
    enabled = enabled,
    readOnly = readOnly,
    textStyle = textStyle,
    label = label,
    placeholder = placeholder,
    leadingIcon = leadingIcon,
    trailingIcon = trailingIcon,
    isError = isError,
    visualTransformation = visualTransformation,
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    singleLine = singleLine,
    maxLines = maxLines,
    minLines = minLines,
    interactionSource = interactionSource,
    colors = colors
)

@Composable
fun LorcanaOutlinedEditText(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChanged: (value: TextFieldValue) -> Unit,
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = BorderStroke(
        ButtonDefaults.OutlinedBorderSize,
        MaterialTheme.colors.primary
    ),
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle.Default,
    label: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = MaterialTheme.colors.onSurface
    )
) = OutlinedTextField(
    modifier = modifier,
    value = value,
    onValueChange = onValueChanged,
    shape = shape,
    enabled = enabled,
    readOnly = readOnly,
    textStyle = textStyle,
    label = label,
    placeholder = placeholder,
    leadingIcon = leadingIcon,
    trailingIcon = trailingIcon,
    isError = isError,
    visualTransformation = visualTransformation,
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    singleLine = singleLine,
    maxLines = maxLines,
    minLines = minLines,
    interactionSource = interactionSource,
    colors = colors
)

@Preview(widthDp = 250)
@Composable
private fun LorcanaOutLinedButtonPreviewDark() =
    MyApplicationTheme(darkTheme = true) {
        LorcanaOutlinedEditText(
            value = "dark",
            onValueChanged = {}
        )
    }

@Preview(widthDp = 250)
@Composable
private fun LorcanaOutLinedButtonPreviewLight() =
    MyApplicationTheme(darkTheme = false) {
        LorcanaOutlinedEditText(
            value = "light",
            onValueChanged = { value -> println(value) }
        )
    }

