package ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BetterOrioksTheme(isDarkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {

    val colorScheme = if (isDarkTheme) darkColorScheme else lightColorScheme

    val typography = Typography(
        labelSmall = MaterialTheme.typography.labelSmall.copy(fontSize = 14.sp),
        labelMedium = MaterialTheme.typography.labelMedium.copy(fontSize = 14.sp, color = colorScheme.onSurfaceVariant)
    )

    val shapes = Shapes(
        extraSmall = RoundedCornerShape(3.0.dp),
        small = RoundedCornerShape(6.0.dp),
        medium = RoundedCornerShape(16.dp),
        large = RoundedCornerShape(16.dp)
    )

    MaterialTheme(colorScheme = colorScheme, typography = typography, shapes = shapes, content = content)
}
