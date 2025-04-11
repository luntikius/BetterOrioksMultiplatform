package ui.loginScreen

import PlatformOs
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.help
import betterorioks.composeapp.generated.resources.news
import betterorioks.composeapp.generated.resources.notifications
import betterorioks.composeapp.generated.resources.onboarding_slide_1_button
import betterorioks.composeapp.generated.resources.onboarding_slide_1_subtitle
import betterorioks.composeapp.generated.resources.onboarding_slide_1_title
import betterorioks.composeapp.generated.resources.onboarding_slide_2_bullet_1_subtitle
import betterorioks.composeapp.generated.resources.onboarding_slide_2_bullet_1_title
import betterorioks.composeapp.generated.resources.onboarding_slide_2_bullet_2_subtitle
import betterorioks.composeapp.generated.resources.onboarding_slide_2_bullet_2_title
import betterorioks.composeapp.generated.resources.onboarding_slide_2_bullet_3_subtitle
import betterorioks.composeapp.generated.resources.onboarding_slide_2_bullet_3_title
import betterorioks.composeapp.generated.resources.onboarding_slide_2_bullet_4_subtitle
import betterorioks.composeapp.generated.resources.onboarding_slide_2_bullet_4_title
import betterorioks.composeapp.generated.resources.onboarding_slide_2_button
import betterorioks.composeapp.generated.resources.onboarding_slide_2_title
import betterorioks.composeapp.generated.resources.onboarding_slide_3_bullet_1_subtitle
import betterorioks.composeapp.generated.resources.onboarding_slide_3_bullet_1_title
import betterorioks.composeapp.generated.resources.onboarding_slide_3_bullet_2_subtitle
import betterorioks.composeapp.generated.resources.onboarding_slide_3_bullet_2_title
import betterorioks.composeapp.generated.resources.onboarding_slide_3_bullet_3_subtitle
import betterorioks.composeapp.generated.resources.onboarding_slide_3_bullet_3_title
import betterorioks.composeapp.generated.resources.onboarding_slide_3_button
import betterorioks.composeapp.generated.resources.onboarding_slide_3_title
import betterorioks.composeapp.generated.resources.profile
import betterorioks.composeapp.generated.resources.schedule
import betterorioks.composeapp.generated.resources.settings
import betterorioks.composeapp.generated.resources.subjects
import getPlatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.common.Bullet
import ui.common.GradientButton
import ui.common.LargeSpacer
import ui.common.MediumSpacer
import ui.common.XLargeSpacer

@Composable
fun Slide1(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.weight(1f))
        MediumSpacer()
        StaticLogo(showSubtitle = false)
        LargeSpacer()
        Text(
            stringResource(Res.string.onboarding_slide_1_title),
            style = MaterialTheme.typography.headlineMedium
        )
        LargeSpacer()
        Text(
            stringResource(Res.string.onboarding_slide_1_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        MediumSpacer()
        Spacer(Modifier.weight(1f))
        GradientButton(
            text = stringResource(Res.string.onboarding_slide_1_button),
            onClick = onClick,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
        XLargeSpacer()
    }
}

@Composable
fun Slide2(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        MediumSpacer()
        Text(
            stringResource(Res.string.onboarding_slide_2_title),
            style = MaterialTheme.typography.headlineMedium
        )
        XLargeSpacer()
        Bullet(
            title = stringResource(Res.string.onboarding_slide_2_bullet_1_title),
            subtitle = stringResource(Res.string.onboarding_slide_2_bullet_1_subtitle),
            image = painterResource(Res.drawable.schedule)
        )
        XLargeSpacer()
        Bullet(
            title = stringResource(Res.string.onboarding_slide_2_bullet_2_title),
            subtitle = stringResource(Res.string.onboarding_slide_2_bullet_2_subtitle),
            image = painterResource(Res.drawable.subjects)
        )
        XLargeSpacer()
        Bullet(
            title = stringResource(Res.string.onboarding_slide_2_bullet_3_title),
            subtitle = stringResource(Res.string.onboarding_slide_2_bullet_3_subtitle),
            image = painterResource(Res.drawable.news)
        )
        if (getPlatform().os == PlatformOs.Android) {
            XLargeSpacer()
            Bullet(
                title = stringResource(Res.string.onboarding_slide_2_bullet_4_title),
                subtitle = stringResource(Res.string.onboarding_slide_2_bullet_4_subtitle),
                image = painterResource(Res.drawable.notifications)
            )
        }
        MediumSpacer()
        Spacer(Modifier.weight(1f))
        GradientButton(
            text = stringResource(Res.string.onboarding_slide_2_button),
            onClick = onClick,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
        XLargeSpacer()
    }
}

@Composable
fun Slide3(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        MediumSpacer()
        Text(
            stringResource(Res.string.onboarding_slide_3_title),
            style = MaterialTheme.typography.headlineMedium
        )
        XLargeSpacer()
        Bullet(
            title = stringResource(Res.string.onboarding_slide_3_bullet_1_title),
            subtitle = stringResource(Res.string.onboarding_slide_3_bullet_1_subtitle),
            image = painterResource(Res.drawable.profile)
        )
        XLargeSpacer()
        Bullet(
            title = stringResource(Res.string.onboarding_slide_3_bullet_2_title),
            subtitle = stringResource(Res.string.onboarding_slide_3_bullet_2_subtitle),
            image = painterResource(Res.drawable.settings)
        )
        XLargeSpacer()
        Bullet(
            title = stringResource(Res.string.onboarding_slide_3_bullet_3_title),
            subtitle = stringResource(Res.string.onboarding_slide_3_bullet_3_subtitle),
            image = painterResource(Res.drawable.help)
        )
        MediumSpacer()
        Spacer(Modifier.weight(1f))
        GradientButton(
            text = stringResource(Res.string.onboarding_slide_3_button),
            onClick = onClick,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
        XLargeSpacer()
    }
}
