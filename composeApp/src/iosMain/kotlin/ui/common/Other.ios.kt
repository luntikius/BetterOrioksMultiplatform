package ui.common

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable

@Composable
actual fun UpdateEdgeToEdge(darkVariant: Boolean) = Unit

const val ANIMATION_DURATION = 300

actual fun getSlideInAnimation() = slideInHorizontally(
    animationSpec = tween(
        durationMillis = ANIMATION_DURATION,
        easing = LinearEasing
    )
) { it }

actual fun getSlideOutAnimation() = slideOutHorizontally(
    animationSpec = tween(
        durationMillis = ANIMATION_DURATION,
        easing = LinearEasing
    )
) { it }

actual fun getAnimationDuration() = ANIMATION_DURATION