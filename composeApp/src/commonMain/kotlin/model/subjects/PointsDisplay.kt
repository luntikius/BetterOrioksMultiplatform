package model.subjects

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.common.LocalColoredBorders
import ui.theme.bad_mark_color
import ui.theme.excellent_mark_color
import ui.theme.good_mark_color
import ui.theme.ok_mark_color

interface PointsDisplay {
    val currentPoints: String
    val maxPoints: String

    @Composable
    fun getPointsColor(): Color {
        return if (currentPoints.toDoubleOrNull() != null && maxPoints.toDoubleOrNull() != null) {
            if (maxPoints.toDouble() == 0.0) {
                MaterialTheme.colorScheme.primary
            } else {
                val percentage = (currentPoints.toDouble() / maxPoints.toDouble().coerceAtMost(100.0) * 100).toInt()
                when (percentage) {
                    in Int.MIN_VALUE..49 -> bad_mark_color
                    in 50..69 -> ok_mark_color
                    in 70..85 -> good_mark_color
                    else -> excellent_mark_color
                }
            }
        } else {
            when (currentPoints.lowercase()) {
                "Н" -> bad_mark_color
                "-" -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.primary
            }
        }
    }

    private fun formatDouble(number: String): String {
        val double = number.toDoubleOrNull() ?: return number
        return if (double % 1.0 == 0.0) {
            double.toInt().toString()
        } else {
            ((double * 100.0).toInt() / 100.0).toString()
        }
    }

    @Composable
    fun getBorder(): BorderStroke? {
        return if (LocalColoredBorders.current.enabled) {
            BorderStroke(
                width = 3.dp,
                color = getPointsColor()
            )
        } else {
            null
        }
    }

    @Composable
    fun getPointsAnnotatedString(): AnnotatedString {
        val color = if (LocalColoredBorders.current.enabled) {
            MaterialTheme.colorScheme.onBackground
        } else {
            getPointsColor()
        }
        return AnnotatedString.Builder().apply {
            withStyle(
                style = MaterialTheme.typography.titleMedium.toSpanStyle()
                    .copy(color = color, fontWeight = FontWeight.Black)
            ) {
                append(formatDouble(currentPoints))
            }

            withStyle(
                style = MaterialTheme.typography.labelSmall.toSpanStyle()
                    .copy(fontSize = 12.sp)
            ) {
                append(" /")
                append(formatDouble(maxPoints))
            }
        }.toAnnotatedString()
    }
}
