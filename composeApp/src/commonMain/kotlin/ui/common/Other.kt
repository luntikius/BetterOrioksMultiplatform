package ui.common

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.arrow_left
import betterorioks.composeapp.generated.resources.back_button
import betterorioks.composeapp.generated.resources.clouds
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.theme.gradientColor1
import ui.theme.gradientColor3

data class ColoredBorders(
    val enabled: Boolean = false
)

val LocalColoredBorders = compositionLocalOf { ColoredBorders() }

@Composable
fun Bullet(
    title: String,
    subtitle: String,
    image: Painter,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            painter = image,
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        LargeSpacer()
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
fun EmptyItem(
    title: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(Res.drawable.clouds),
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )
        LargeSpacer()
        Text(
            title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Composable
fun DefaultHeader(
    text: String,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttons: @Composable () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        IconButton(onClick = onBackButtonClick) {
            Icon(
                painter = painterResource(Res.drawable.arrow_left),
                contentDescription = stringResource(Res.string.back_button),
                modifier = Modifier.size(32.dp)
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.weight(1f)
        )
        buttons()
    }
}

@Composable
fun gradientColor2() = MaterialTheme.colorScheme.primary

@Composable
fun GradientIcon(
    painter: Painter,
    contentDescription: String? = null,
    modifier: Modifier = Modifier
) {
    val horizontalGradientBrush = Brush.horizontalGradient(
        colors = listOf(
            gradientColor1,
            gradientColor2(),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeRefreshBox(
    onSwipeRefresh: () -> Unit,
    isRefreshing: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val state = rememberPullToRefreshState()
    var shouldAnimateToHidden by remember { mutableStateOf(false) }

    LaunchedEffect(shouldAnimateToHidden) {
        if (shouldAnimateToHidden) {
            state.animateToHidden()
            shouldAnimateToHidden = false
        }
    }
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            onSwipeRefresh()
            shouldAnimateToHidden = true
        },
        modifier = modifier,
        state = state
    ) {
        content()
    }
}

@Composable
expect fun UpdateEdgeToEdge(darkVariant: Boolean)

expect fun getSlideInAnimation(): EnterTransition

expect fun getSlideOutAnimation(): ExitTransition

expect fun getAnimationDuration(): Int
