/* ktlint-disable wrapping indent argument-list-wrapping no-unused-imports */
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

public val LorcanaIcons.Defense: ImageVector
    get() {
        if (_defense != null) {
            return _defense!!
        }
        _defense = Builder(name = "Defense", defaultWidth = 80.0.dp, defaultHeight = 98.0.dp,
                viewportWidth = 80.0f, viewportHeight = 98.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 2.5f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(33.8627f, 12.2092f)
                lineTo(45.8095f, 12.2092f)
                lineTo(54.1475f, 1.1336f)
                lineTo(70.3254f, 4.9915f)
                lineTo(77.7921f, 24.6532f)
                lineTo(66.2184f, 35.8539f)
                curveTo(66.2184f, 35.8539f, 65.2231f, 41.7023f, 65.4713f, 49.7912f)
                lineTo(77.9162f, 61.365f)
                lineTo(39.089f, 96.0853f)
                lineTo(1.5069f, 61.4891f)
                lineTo(12.9557f, 50.7874f)
                curveTo(12.9557f, 50.7874f, 13.4538f, 38.9646f, 12.5826f, 35.4798f)
                lineTo(1.1329f, 24.1561f)
                lineTo(8.4756f, 5.8627f)
                lineTo(26.2709f, 1.5067f)
                lineTo(33.8627f, 12.2092f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 3.12f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(5.5767f, 15.9461f)
                curveTo(5.5767f, 15.9461f, 42.4188f, -2.3547f, 73.6847f, 17.348f)
                curveTo(73.6847f, 17.348f, 60.3911f, 27.8064f, 67.5306f, 59.6703f)
                lineTo(39.8336f, 83.1432f)
                lineTo(13.3683f, 59.7925f)
                curveTo(13.3683f, 59.7925f, 19.328f, 29.1384f, 5.5767f, 15.9461f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 2.07f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(5.862f, 29.0949f)
                curveTo(5.862f, 29.0949f, 8.7247f, 40.974f, 5.3648f, 57.507f)
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 2.22f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(74.2612f, 28.3766f)
                curveTo(74.2612f, 28.3766f, 71.679f, 41.7892f, 74.4765f, 58.1292f)
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 2.05f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(67.6955f, 59.2888f)
                curveTo(67.6955f, 59.2888f, 68.9398f, 62.8977f, 71.5534f, 66.7556f)
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 2.05f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(12.9673f, 60.0187f)
                curveTo(12.9673f, 60.0187f, 10.7277f, 64.2497f, 7.9901f, 67.1124f)
            }
        }
        .build()
        return _defense!!
    }

private var _defense: ImageVector? = null
