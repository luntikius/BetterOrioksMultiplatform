package ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import model.settings.Theme

@Composable
fun BetterOrioksTheme(theme: Theme, content: @Composable () -> Unit) {
    val colorScheme = when (theme) {
        Theme.System -> { if (isSystemInDarkTheme()) darkColorScheme else lightColorScheme }
        Theme.Dark -> darkColorScheme
        Theme.Light -> lightColorScheme
    }

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
