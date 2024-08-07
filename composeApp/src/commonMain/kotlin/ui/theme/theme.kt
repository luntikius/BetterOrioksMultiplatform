package ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BetterOrioksTheme(isDarkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {

    val lightColorScheme = lightColorScheme(primary = Color(0xFF1EB980))

    val darkColorScheme = darkColorScheme(primary = Color(0xFF66ffc7))

    val colorScheme = if(isDarkTheme) darkColorScheme else lightColorScheme


    val typography = Typography(
            displaySmall = TextStyle(fontWeight = FontWeight.W100, fontSize = 96.sp),
            labelLarge = TextStyle(fontWeight = FontWeight.W600, fontSize = 14.sp)
        )

    val shapes = Shapes(extraSmall = RoundedCornerShape(3.0.dp), small = RoundedCornerShape(6.0.dp), medium = RoundedCornerShape(16.dp), large = RoundedCornerShape(16.dp))

    MaterialTheme(colorScheme = colorScheme, typography = typography, shapes = shapes, content = content)
}