package ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.arrow_right
import betterorioks.composeapp.generated.resources.checked_circle
import betterorioks.composeapp.generated.resources.content_description_disabled
import betterorioks.composeapp.generated.resources.content_description_enabled
import betterorioks.composeapp.generated.resources.unchecked_circle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.theme.gradientColor1
import ui.theme.gradientColor3

@Composable
fun SimpleIconButton(
    icon: Painter,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    iconSize: Int = 36,
) {
    val alpha = if (enabled) 1f else 0.5f
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier,
        enabled = enabled,
        color = Color.Transparent
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                painter = icon,
                contentDescription = text,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = alpha),
                modifier = Modifier.size(iconSize.dp)
            )
            SmallSpacer()
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = alpha)
            )
        }
    }
}

@Composable
fun HorizontalIconTextButton(
    painter: Painter,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.Transparent,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick, enabled = enabled)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
        ) {
            Icon(
                painter = painter,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            LargeSpacer()
            Text(
                text,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Icon(
                painter = painterResource(Res.drawable.arrow_right),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

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
            gradientColor2(),
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
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                color = Color.White.copy(alpha = alpha),
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Composable
fun ToggleButton(
    isChecked: Boolean,
    iconPainter: Painter,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (checkboxPainter, checkboxDescription, checkboxTint) = if (isChecked) {
        Triple(
            painterResource(Res.drawable.checked_circle),
            stringResource(Res.string.content_description_enabled),
            MaterialTheme.colorScheme.primary
        )
    } else {
        Triple(
            painterResource(Res.drawable.unchecked_circle),
            stringResource(Res.string.content_description_disabled),
            MaterialTheme.colorScheme.background
        )
    }

    Card(
        modifier = modifier,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(vertical = 16.dp).align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = iconPainter,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp),
                    tint = MaterialTheme.colorScheme.primary,
                )
                Text(text)
            }
            Icon(
                painter = checkboxPainter,
                contentDescription = checkboxDescription,
                tint = checkboxTint,
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = (-8).dp, y = 8.dp)
            )
        }
    }
}
