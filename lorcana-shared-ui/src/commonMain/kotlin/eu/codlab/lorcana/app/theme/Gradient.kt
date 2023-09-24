package eu.codlab.lorcana.app.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

private const val Start = 0.0f
private const val End = 1000.0f

fun Modifier.gradient(
    startColor: Color = Color.Black,
    endColor: Color = Color.White
): Modifier {
    val gradient = Brush.linearGradient(
        Start to startColor,
        End to endColor,
        start = Offset.Zero,
        end = Offset.Infinite
    )
    return this.background(gradient)
}

@Preview(widthDp = 400, heightDp = 400)
@Composable
private fun GradientSample() {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .gradient(
                        startColor = AppColor.Primary,
                        endColor = AppColor.Blue
                    )
            ) {}
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .gradient(
                        startColor = AppColor.PrimaryLight,
                        endColor = AppColor.BlueLight
                    )
            ) {}
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .gradient()
            ) {}
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .gradient()
            ) {}
        }
    }
}
