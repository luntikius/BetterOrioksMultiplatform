package ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import model.settings.SettingsState
import model.settings.Theme
import ui.common.UpdateEdgeToEdge

@Composable
fun BetterOrioksTheme(
    settings: SettingsState,
    content: @Composable () -> Unit,
) = settings.run {
    val colorScheme = when (theme) {
        Theme.System -> {
            if (isSystemInDarkTheme()) {
                darkColorScheme(softenDarkTheme, pinkMode)
            } else {
                lightColorScheme(pinkMode)
            }
        }
        Theme.Dark -> darkColorScheme(softenDarkTheme, pinkMode)
        Theme.Light -> lightColorScheme(pinkMode)
    }
    val shouldBeDark = theme == Theme.Dark || (theme == Theme.System && isSystemInDarkTheme())
    UpdateEdgeToEdge(shouldBeDark)

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
