/* ktlint-disable wrapping indent argument-list-wrapping no-unused-imports */
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

public val LorcanaIcons.Exert: ImageVector
    get() {
        if (_exert != null) {
            return _exert!!
        }
        _exert = Builder(name = "Exert", defaultWidth = 30.0.dp, defaultHeight = 35.0.dp,
                viewportWidth = 30.0f, viewportHeight = 35.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(14.997f, 0.0f)
                lineTo(29.995f, 8.659f)
                lineTo(29.995f, 25.976f)
                lineTo(14.997f, 34.635f)
                lineTo(0.0f, 25.976f)
                lineTo(0.0f, 8.659f)
                lineTo(14.997f, 0.0f)
                close()
                moveTo(18.692f, 26.912f)
                curveTo(15.362f, 26.807f, 8.351f, 25.756f, 8.294f, 18.757f)
                curveTo(8.218f, 9.575f, 18.529f, 11.381f, 18.529f, 11.381f)
                curveTo(18.529f, 11.381f, 19.252f, 10.123f, 19.764f, 9.234f)
                curveTo(19.86f, 9.066f, 20.041f, 8.967f, 20.234f, 8.975f)
                curveTo(20.427f, 8.983f, 20.599f, 9.098f, 20.68f, 9.273f)
                curveTo(21.408f, 10.838f, 22.887f, 14.017f, 23.51f, 15.355f)
                curveTo(23.578f, 15.502f, 23.574f, 15.672f, 23.499f, 15.814f)
                curveTo(23.424f, 15.957f, 23.286f, 16.057f, 23.126f, 16.083f)
                curveTo(21.713f, 16.317f, 18.421f, 16.862f, 16.723f, 17.143f)
                curveTo(16.528f, 17.175f, 16.331f, 17.093f, 16.217f, 16.931f)
                curveTo(16.103f, 16.769f, 16.091f, 16.556f, 16.187f, 16.383f)
                curveTo(16.648f, 15.552f, 17.25f, 14.467f, 17.25f, 14.467f)
                curveTo(9.724f, 13.789f, 12.885f, 20.939f, 12.885f, 20.939f)
                curveTo(15.652f, 24.079f, 19.322f, 23.418f, 21.235f, 22.762f)
                curveTo(21.235f, 22.762f, 27.233f, 12.972f, 28.588f, 10.761f)
                curveTo(28.66f, 10.644f, 28.682f, 10.504f, 28.65f, 10.37f)
                curveTo(28.618f, 10.237f, 28.535f, 10.122f, 28.418f, 10.051f)
                curveTo(26.332f, 8.773f, 17.464f, 3.338f, 15.378f, 2.06f)
                curveTo(15.135f, 1.911f, 14.817f, 1.988f, 14.668f, 2.231f)
                curveTo(12.915f, 5.092f, 3.159f, 21.013f, 1.406f, 23.874f)
                curveTo(1.257f, 24.117f, 1.333f, 24.435f, 1.576f, 24.584f)
                curveTo(3.662f, 25.862f, 12.531f, 31.297f, 14.616f, 32.575f)
                curveTo(14.859f, 32.724f, 15.177f, 32.647f, 15.326f, 32.404f)
                curveTo(16.169f, 31.03f, 18.692f, 26.912f, 18.692f, 26.912f)
                close()
            }
        }
        .build()
        return _exert!!
    }

private var _exert: ImageVector? = null
