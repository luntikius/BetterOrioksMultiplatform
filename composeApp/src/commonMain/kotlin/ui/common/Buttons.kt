package ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.theme.gradientColor1
import ui.theme.gradientColor2
import ui.theme.gradientColor3

@Composable
fun CardButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceDim,
            contentColor = textColor,
            disabledContentColor = textColor.copy(alpha = 0.5f)
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val horizontalGradientBrush = Brush.horizontalGradient(
        colors = listOf(
            gradientColor1,
            gradientColor2,
            gradientColor3
        )
    )

    val alpha = if (enabled) 1f else 0.5f

    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        contentPadding = PaddingValues(),
        onClick = onClick,
        enabled = enabled
    ) {
        Box(
            modifier = Modifier
                .background(horizontalGradientBrush, shape = CircleShape, alpha = alpha)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White.copy(alpha = alpha),
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}
