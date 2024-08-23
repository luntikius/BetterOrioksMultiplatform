package ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val black = Color(0xFF000000)
val white = Color(0xFFFFFFFF)

val gray100 = Color(0xFFEDEEF0)
val gray200 = Color(0xFFF0F4F7)
val gray300 = Color(0xFF99A2BE)
val gray500 = Color(0xFF303035)
val gray600 = Color(0xFF222228)
val gray800 = Color(0xFF141419)

val primary_light_theme = Color(0xFF0088FF)
val primary_dark_theme = Color(0xFF72BDFF)

val lightColorScheme = lightColorScheme(
    primary = primary_light_theme,
    background = gray100,
    surface = white,
    surfaceTint = gray200,
    onSurface = black,
    onSurfaceVariant = gray300,
    onBackground = black,
)

val darkColorScheme = darkColorScheme(
    primary = primary_dark_theme,
    background = black,
    surface = gray600,
    surfaceTint = gray800,
    onSurface = white,
    onSurfaceVariant = gray300,
    onBackground = white
)