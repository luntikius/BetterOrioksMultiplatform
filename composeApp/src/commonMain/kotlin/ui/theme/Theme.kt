package ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun BetterOrioksTheme(isDarkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {

    val colorScheme = if (isDarkTheme) darkColorScheme else lightColorScheme

    val shapes = Shapes(
        extraSmall = RoundedCornerShape(16.dp),
        small = RoundedCornerShape(16.dp),
        medium = RoundedCornerShape(16.dp),
        large = RoundedCornerShape(16.dp)
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = BetterOrioksTypography(colorScheme),
        shapes = shapes,
        content = content
    )
}
