package utils

import androidx.compose.ui.graphics.Color

fun Color.disabled(): Color {
    return this.copy(alpha = 0.5f)
}