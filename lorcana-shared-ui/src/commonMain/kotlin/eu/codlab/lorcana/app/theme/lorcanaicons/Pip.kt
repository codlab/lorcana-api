package eu.codlab.lorcana.app.theme.lorcanaicons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import eu.codlab.lorcana.app.theme.LorcanaIcons

public val LorcanaIcons.Pip: ImageVector
    get() {
        if (_pip != null) {
            return _pip!!
        }
        _pip = Builder(name = "Pip", defaultWidth = 36.0.dp, defaultHeight = 51.0.dp, viewportWidth
                = 36.0f, viewportHeight = 51.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(17.6005f, 50.5029f)
                curveTo(12.3806f, 40.34f, 2.2736f, 34.1379f, 0.3815f, 33.0409f)
                curveTo(0.3085f, 33.001f, 0.2595f, 32.928f, 0.2486f, 32.8459f)
                curveTo(0.2376f, 32.7629f, 0.2666f, 32.681f, 0.3266f, 32.6229f)
                curveTo(16.6326f, 16.8389f, 17.5955f, -1.0E-4f, 17.5955f, -1.0E-4f)
                curveTo(17.7666f, 1.1919f, 19.2916f, 17.447f, 35.2636f, 32.866f)
                curveTo(35.2636f, 32.866f, 23.4176f, 39.3119f, 17.6685f, 50.6359f)
                lineTo(17.6005f, 50.5029f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(17.6679f, 19.1486f)
                lineTo(27.055f, 32.0497f)
                lineTo(17.6679f, 39.2432f)
                lineTo(8.5289f, 32.0497f)
                lineTo(17.6679f, 19.1486f)
                close()
            }
        }
        .build()
        return _pip!!
    }

private var _pip: ImageVector? = null
