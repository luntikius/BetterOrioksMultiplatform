package ui.common

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import ui.theme.gradientColor1
import ui.theme.gradientColor2
import ui.theme.gradientColor3

@Composable
fun GradientIcon(
    painter: Painter,
    contentDescription: String? = null,
    modifier: Modifier = Modifier
) {
    val horizontalGradientBrush = Brush.horizontalGradient(
        colors = listOf(
            gradientColor1,
            gradientColor2,
            gradientColor3
        )
    )

    Icon(
        painter,
        contentDescription,
        modifier
            .graphicsLayer(alpha = 0.99f)
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    drawRect(horizontalGradientBrush, blendMode = BlendMode.SrcAtop)
                }
            }
    )
}
