package eu.codlab.lorcana.app.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import eu.codlab.lorcana.app.theme.lorcanaicons.Cost
import eu.codlab.lorcana.app.theme.lorcanaicons.Defense
import eu.codlab.lorcana.app.theme.lorcanaicons.Exert
import eu.codlab.lorcana.app.theme.lorcanaicons.Inkpot
import eu.codlab.lorcana.app.theme.lorcanaicons.Pip
import eu.codlab.lorcana.app.theme.lorcanaicons.Strength

@Preview
@Composable
fun PreviewIcons() {
    Column {
        listOf(
            LorcanaIcons.Cost,
            LorcanaIcons.Defense,
            LorcanaIcons.Exert,
            LorcanaIcons.Inkpot,
            LorcanaIcons.Pip,
            LorcanaIcons.Strength
        ).map {
            Image(
                imageVector = it,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Blue)
            )
        }
    }
}