package ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val black = Color(0xFF000000)
val almostBlack = Color(0xFF121214)
val white = Color(0xFFFFFFFF)

val gray100 = Color(0xFFEDEEF0)
val gray200 = Color(0xFFF0F4F7)
val gray300 = Color(0xFF99A2BE)

// val gray500 = Color(0xFF303035)

val gray600 = Color(0xFF222228)
val gray800 = Color(0xFF141419)
val error_light_theme = Color(0xFFBB0A1E)
val error_dark_theme = Color(0xFFC83A4A)

val gradientColor1 = Color(160, 159, 255, 255)
val gradientColor3 = Color(136, 216, 222, 255)

val primary_light_theme = Color(0xFF0088FF)
val primary_light_theme_w = Color(0xFFFF1493)
val primary_dark_theme = Color(0xFF72BDFF)
val primary_dark_theme_w = Color(0xFFFF6EC7)

val excellent_mark_color = Color(0xFF147014)
val good_mark_color = Color(0xFF70B12C)
val ok_mark_color = Color(0xFFF8CC1B)
val bad_mark_color = Color(0xFFD12729)

fun lightColorScheme(
    pinkMode: Boolean
) = lightColorScheme(
    primary = if (pinkMode) primary_light_theme_w else primary_light_theme,
    background = gray100,
    surface = white,
    surfaceContainer = white,
    surfaceContainerLow = white,
    surfaceContainerHigh = white,
    surfaceContainerLowest = white,
    surfaceContainerHighest = white,
    surfaceTint = gray200,
    onSurface = black,
    onSurfaceVariant = gray300,
    onBackground = black,
    error = error_light_theme
)

fun darkColorScheme(
    soften: Boolean,
    pinkMode: Boolean
) = darkColorScheme(
    primary = if (pinkMode) primary_dark_theme_w else primary_dark_theme,
    background = if (soften) almostBlack else black,
    surface = gray600,
    surfaceContainerLow = gray600,
    surfaceContainerHigh = gray600,
    surfaceContainerLowest = gray600,
    surfaceContainerHighest = gray600,
    surfaceTint = gray800,
    onSurface = white,
    onSurfaceVariant = gray300,
    onBackground = white,
    error = error_dark_theme
)
