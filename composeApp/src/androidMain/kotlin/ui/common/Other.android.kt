package ui.common

import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView

@Composable
actual fun UpdateEdgeToEdge(darkVariant: Boolean) {
    val view = LocalView.current
    if (view.isInEditMode) return

    SideEffect {
        val barStyle = if (darkVariant) {
            SystemBarStyle.dark(Color.TRANSPARENT)
        } else {
            SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        }
        (view.context as ComponentActivity).enableEdgeToEdge(barStyle, barStyle)
    }
}

const val ANIMATION_DURATION = 500

actual fun getSlideInAnimation() =
    fadeIn(animationSpec = tween(ANIMATION_DURATION)) +
        slideInHorizontally(animationSpec = tween(ANIMATION_DURATION)) { it / 2 }

actual fun getSlideOutAnimation() =
    fadeOut(animationSpec = tween(ANIMATION_DURATION)) +
        slideOutHorizontally(animationSpec = tween(ANIMATION_DURATION)) { it / 2 }

actual fun getAnimationDuration() = ANIMATION_DURATION