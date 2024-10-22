package model.subjects

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

data class DisplaySubject(
    val name: String,
    val currentPoints: String,
    val maxPoints: String,
    val color: Color
) {
    constructor(name: String, currentPoints: Number, maxPoints: Number, color: Color) :
        this(name, currentPoints.toString(), maxPoints.toString(), color)

    constructor(name: String, currentPoints: String, maxPoints: Number, color: Color) :
        this(name, currentPoints, maxPoints.toString(), color)

    @Composable
    fun getPointsAnnotatedString(color: Color): AnnotatedString {
        return AnnotatedString.Builder().apply {
            withStyle(
                style = MaterialTheme.typography.titleMedium.toSpanStyle()
                    .copy(color = color, fontWeight = FontWeight.Black)
            ) {
                append(currentPoints)
            }

            withStyle(
                style = MaterialTheme.typography.labelSmall.toSpanStyle()
                    .copy(fontSize = 12.sp)
            ) {
                append(" /")
                append(maxPoints)
            }
        }.toAnnotatedString()
    }
}
