/* ktlint-disable filename indent op-spacing */
@file:Suppress("MatchingDeclarationName", "TopLevelPropertyNaming")

package eu.codlab.lorcana.app.theme

import androidx.compose.ui.graphics.vector.ImageVector
import eu.codlab.lorcana.app.theme.lorcanaicons.Cost
import eu.codlab.lorcana.app.theme.lorcanaicons.Defense
import eu.codlab.lorcana.app.theme.lorcanaicons.Exert
import eu.codlab.lorcana.app.theme.lorcanaicons.Inkpot
import eu.codlab.lorcana.app.theme.lorcanaicons.Pip
import eu.codlab.lorcana.app.theme.lorcanaicons.Strength
import kotlin.collections.List as ____KtList

public object LorcanaIcons

private var __Icon: ____KtList<ImageVector>? = null

public val LorcanaIcons.Icon: ____KtList<ImageVector>
  get() {
    if (__Icon != null) {
      return __Icon!!
    }
    __Icon= listOf(Inkpot, Cost, Pip, Defense, Strength, Exert)
    return __Icon!!
  }
