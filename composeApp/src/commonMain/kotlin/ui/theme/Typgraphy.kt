package ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import betterorioks.composeapp.generated.resources.Lato_Black
import betterorioks.composeapp.generated.resources.Lato_BlackItalic
import betterorioks.composeapp.generated.resources.Lato_Bold
import betterorioks.composeapp.generated.resources.Lato_BoldItalic
import betterorioks.composeapp.generated.resources.Lato_ExtraBold
import betterorioks.composeapp.generated.resources.Lato_ExtraBoldItalic
import betterorioks.composeapp.generated.resources.Lato_ExtraLight
import betterorioks.composeapp.generated.resources.Lato_ExtraLightItalic
import betterorioks.composeapp.generated.resources.Lato_Italic
import betterorioks.composeapp.generated.resources.Lato_Light
import betterorioks.composeapp.generated.resources.Lato_LightItalic
import betterorioks.composeapp.generated.resources.Lato_Medium
import betterorioks.composeapp.generated.resources.Lato_MediumItalic
import betterorioks.composeapp.generated.resources.Lato_Regular
import betterorioks.composeapp.generated.resources.Lato_SemiBold
import betterorioks.composeapp.generated.resources.Lato_SemiBoldItalic
import betterorioks.composeapp.generated.resources.Lato_Thin
import betterorioks.composeapp.generated.resources.Lato_ThinItalic
import betterorioks.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun BetterOrioksTypography(colorScheme: ColorScheme): Typography {
    val betterOrioksFontFamily = FontFamily(
        Font(Res.font.Lato_Black, FontWeight.Black),
        Font(Res.font.Lato_BlackItalic, FontWeight.Black, FontStyle.Italic),
        Font(Res.font.Lato_Bold, FontWeight.Bold),
        Font(Res.font.Lato_BoldItalic, FontWeight.Bold, FontStyle.Italic),
        Font(Res.font.Lato_ExtraBold, FontWeight.ExtraBold),
        Font(Res.font.Lato_ExtraBoldItalic, FontWeight.ExtraBold, FontStyle.Italic),
        Font(Res.font.Lato_ExtraLight, FontWeight.ExtraLight),
        Font(Res.font.Lato_ExtraLightItalic, FontWeight.ExtraLight, FontStyle.Italic),
        Font(Res.font.Lato_Italic, FontWeight.Normal, FontStyle.Italic),
        Font(Res.font.Lato_Light, FontWeight.Light),
        Font(Res.font.Lato_LightItalic, FontWeight.Light, FontStyle.Italic),
        Font(Res.font.Lato_Medium, FontWeight.Medium),
        Font(Res.font.Lato_MediumItalic, FontWeight.Medium, FontStyle.Italic),
        Font(Res.font.Lato_Regular, FontWeight.Normal),
        Font(Res.font.Lato_SemiBold, FontWeight.SemiBold),
        Font(Res.font.Lato_SemiBoldItalic, FontWeight.SemiBold, FontStyle.Italic),
        Font(Res.font.Lato_Thin, FontWeight.Thin),
        Font(Res.font.Lato_ThinItalic, FontWeight.Thin, FontStyle.Italic)
    )

    return Typography(
        displayLarge = MaterialTheme.typography.displayLarge.copy(
            fontFamily = betterOrioksFontFamily,
            fontWeight = FontWeight.Bold
        ),
        displayMedium = MaterialTheme.typography.displayMedium.copy(
            fontFamily = betterOrioksFontFamily,
            fontWeight = FontWeight.Bold
        ),
        displaySmall = MaterialTheme.typography.displaySmall.copy(
            fontFamily = betterOrioksFontFamily,
            fontWeight = FontWeight.Bold
        ),

        headlineLarge = MaterialTheme.typography.headlineLarge.copy(
            fontWeight = FontWeight.Bold,
            fontFamily = betterOrioksFontFamily
        ),
        headlineMedium = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Bold,
            fontFamily = betterOrioksFontFamily
        ),
        headlineSmall = MaterialTheme.typography.headlineSmall.copy(
            fontWeight = FontWeight.Bold,
            fontFamily = betterOrioksFontFamily
        ),

        titleLarge = MaterialTheme.typography.titleLarge.copy(
            fontFamily = betterOrioksFontFamily,
            fontWeight = FontWeight.SemiBold
        ),
        titleMedium = MaterialTheme.typography.titleMedium.copy(
            fontFamily = betterOrioksFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = (MaterialTheme.typography.titleMedium.fontSize.value + 2).sp
        ),
        titleSmall = MaterialTheme.typography.titleSmall.copy(
            fontFamily = betterOrioksFontFamily,
            fontWeight = FontWeight.SemiBold
        ),

        bodyLarge = MaterialTheme.typography.bodyLarge.copy(fontFamily = betterOrioksFontFamily),
        bodyMedium = MaterialTheme.typography.bodyMedium.copy(fontFamily = betterOrioksFontFamily),
        bodySmall = MaterialTheme.typography.bodySmall.copy(fontFamily = betterOrioksFontFamily),

        labelLarge = MaterialTheme.typography.labelLarge.copy(
            fontFamily = betterOrioksFontFamily
        ),
        labelMedium = MaterialTheme.typography.labelMedium.copy(
            fontSize = 16.sp,
            color = colorScheme.onSurfaceVariant,
            fontFamily = betterOrioksFontFamily
        ),
        labelSmall = MaterialTheme.typography.labelSmall.copy(
            fontSize = 14.sp,
            color = colorScheme.onSurfaceVariant,
            fontFamily = betterOrioksFontFamily
        )
    )
}
