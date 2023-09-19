package eu.codlab.lorcana.app.theme.lorcanaicons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import eu.codlab.lorcana.app.theme.LorcanaIcons

public val LorcanaIcons.Cost: ImageVector
    get() {
        if (_cost != null) {
            return _cost!!
        }
        _cost = Builder(name = "Cost", defaultWidth = 68.0.dp, defaultHeight = 79.0.dp,
                viewportWidth = 68.0f, viewportHeight = 79.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(33.9279f, -1.0E-4f)
                lineTo(67.8559f, 19.5879f)
                lineTo(67.8559f, 58.7649f)
                lineTo(33.9279f, 78.3539f)
                lineTo(-1.0E-4f, 58.7649f)
                lineTo(-1.0E-4f, 19.5879f)
                lineTo(33.9279f, -1.0E-4f)
                close()
                moveTo(33.9279f, 5.2489f)
                lineTo(63.3109f, 22.2129f)
                lineTo(63.3109f, 56.1409f)
                lineTo(33.9279f, 73.1049f)
                lineTo(4.5459f, 56.1409f)
                lineTo(4.5459f, 22.2129f)
                lineTo(33.9279f, 5.2489f)
                close()
            }
        }
        .build()
        return _cost!!
    }

private var _cost: ImageVector? = null
