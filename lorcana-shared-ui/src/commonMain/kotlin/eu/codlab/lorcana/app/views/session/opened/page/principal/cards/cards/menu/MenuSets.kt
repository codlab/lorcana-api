package eu.codlab.lorcana.app.views.session.opened.page.principal.cards.cards.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.github.codlab.lorcana.card.LorcanaSet
import eu.codlab.compose.widgets.VectorIcon

@Composable
fun MenuSets(
    modifier: Modifier = Modifier,
    sets: List<LorcanaSet>,
    onSet: (LorcanaSet) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        IconButton(onClick = { expanded = !expanded }) {
            VectorIcon(
                imageVector = Icons.Default.MoreVert
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            sets.map {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = it.name()
                        )
                    },
                    onClick = {
                        expanded = false
                        onSet(it)
                    }
                )
            }
        }
    }
}
