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
import betterorioks.composeapp.generated.resources.LogIn
import betterorioks.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.stringResource
import ui.theme.gradientColor1
import ui.theme.gradientColor2
import ui.theme.gradientColor3

@Composable
fun GradientButton(
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
        ){
            Text(
                stringResource(Res.string.LogIn),
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}