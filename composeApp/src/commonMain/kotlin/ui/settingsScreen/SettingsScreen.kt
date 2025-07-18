package ui.settingsScreen

import PlatformOs
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.adaptive_mode
import betterorioks.composeapp.generated.resources.dark_mode
import betterorioks.composeapp.generated.resources.light_mode
import betterorioks.composeapp.generated.resources.settings
import betterorioks.composeapp.generated.resources.settings_enable_colored_borders_theme_subtitle
import betterorioks.composeapp.generated.resources.settings_enable_colored_borders_theme_title
import betterorioks.composeapp.generated.resources.settings_enable_force_notification_subtitle
import betterorioks.composeapp.generated.resources.settings_enable_force_notification_title
import betterorioks.composeapp.generated.resources.settings_enable_ios_notifications_subtitle
import betterorioks.composeapp.generated.resources.settings_enable_ios_notifications_title
import betterorioks.composeapp.generated.resources.settings_log_notification_activity_title
import betterorioks.composeapp.generated.resources.settings_pink_mode
import betterorioks.composeapp.generated.resources.settings_show_donation_widget_subtitle
import betterorioks.composeapp.generated.resources.settings_show_donation_widget_title
import betterorioks.composeapp.generated.resources.settings_soften_dark_theme_subtitle
import betterorioks.composeapp.generated.resources.settings_soften_dark_theme_title
import betterorioks.composeapp.generated.resources.settings_theme_dark
import betterorioks.composeapp.generated.resources.settings_theme_light
import betterorioks.composeapp.generated.resources.settings_theme_system
import betterorioks.composeapp.generated.resources.settings_title_developer
import betterorioks.composeapp.generated.resources.settings_title_fun
import betterorioks.composeapp.generated.resources.settings_title_functionality
import betterorioks.composeapp.generated.resources.theme
import getPlatform
import model.AppInfo
import model.settings.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.getKoin
import ui.common.DefaultHeader
import ui.common.LargeSpacer
import ui.common.MediumSpacer
import ui.common.SmallSpacer
import ui.common.ToggleButton
import ui.common.XLargeSpacer

@Composable
fun SettingsScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val koin = getKoin()
    val viewModel = viewModel {
        koin.get<SettingsViewModel>()
    }

    LazyColumn(
        modifier = modifier.padding(8.dp)
    ) {
        item {
            DefaultHeader(
                text = stringResource(Res.string.settings),
                onBackButtonClick = { navController.popBackStack() },
            )
            MediumSpacer()
            SettingsTitle(stringResource(Res.string.theme))
            ThemeButtons(viewModel)
            FunctionalitySettings(viewModel)
            HiddenSettings(viewModel)
            XLargeSpacer()
            BuildInfo(
                onClick = viewModel::onBuildNumberClick
            )
        }
    }
}

@Composable
fun SettingsTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        modifier = modifier.padding(16.dp)
    )
}

@Composable
fun ThemeButtons(
    viewModel: SettingsViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedTheme = uiState.selectedTheme
    Row(
        modifier = modifier.heightIn(min = 72.dp, max = 120.dp)
    ) {
        MediumSpacer()
        ToggleButton(
            isChecked = selectedTheme == Theme.System,
            iconPainter = painterResource(Res.drawable.adaptive_mode),
            text = stringResource(Res.string.settings_theme_system),
            onClick = { viewModel.setTheme(Theme.System) },
            modifier = Modifier.weight(1f)
        )
        MediumSpacer()
        ToggleButton(
            isChecked = selectedTheme == Theme.Light,
            iconPainter = painterResource(Res.drawable.light_mode),
            text = stringResource(Res.string.settings_theme_light),
            onClick = { viewModel.setTheme(Theme.Light) },
            modifier = Modifier.weight(1f)
        )
        MediumSpacer()
        ToggleButton(
            isChecked = selectedTheme == Theme.Dark,
            iconPainter = painterResource(Res.drawable.dark_mode),
            text = stringResource(Res.string.settings_theme_dark),
            onClick = { viewModel.setTheme(Theme.Dark) },
            modifier = Modifier.weight(1f)
        )
        MediumSpacer()
    }
    LargeSpacer()
    AnimatedVisibilitySettingsItem(
        isVisible = uiState.showSoftenDarkThemeSwitch,
        isChecked = uiState.softenDarkTheme,
        title = stringResource(Res.string.settings_soften_dark_theme_title),
        subtitle = stringResource(Res.string.settings_soften_dark_theme_subtitle),
        onClick = { viewModel.setSoftenDarkTheme(it) }
    )
    LargeSpacer()
    SettingsItem(
        isChecked = uiState.enableColoredBorders,
        title = stringResource(Res.string.settings_enable_colored_borders_theme_title),
        subtitle = stringResource(Res.string.settings_enable_colored_borders_theme_subtitle),
        onClick = { viewModel.enableColoredBorders(it) }
    )
}

@Composable
fun FunctionalitySettings(
    viewModel: SettingsViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    LargeSpacer()
    SettingsTitle(
        text = stringResource(Res.string.settings_title_functionality)
    )
    AnimatedVisibilitySettingsItem(
        isVisible = getPlatform().os == PlatformOs.Android || uiState.enableIosNotifications,
        isChecked = uiState.enableForceNotifications,
        onClick = viewModel::setEnableForceNotification,
        title = stringResource(Res.string.settings_enable_force_notification_title),
        subtitle = stringResource(Res.string.settings_enable_force_notification_subtitle)
    )
    LargeSpacer()
    SettingsItem(
        isChecked = uiState.showDonationWidget,
        onClick = viewModel::setShowDonationWidget,
        title = stringResource(Res.string.settings_show_donation_widget_title),
        subtitle = stringResource(Res.string.settings_show_donation_widget_subtitle)
    )
}

@Composable
fun HiddenSettings(
    viewModel: SettingsViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier.animateContentSize().fillMaxWidth()
    ) {
        if (uiState.showHiddenSettings) {
            FunSettings(
                uiState = uiState,
                viewModel = viewModel,
            )
            DeveloperSettings(
                uiState = uiState,
                viewModel = viewModel,
            )
        }
    }
}

@Composable
fun FunSettings(
    uiState: SettingsUiState,
    viewModel: SettingsViewModel
) {
    LargeSpacer()
    SettingsTitle(
        text = stringResource(Res.string.settings_title_fun)
    )
    SettingsItem(
        isChecked = uiState.pinkMode,
        onClick = viewModel::setPinkMode,
        title = stringResource(Res.string.settings_pink_mode)
    )
    if (getPlatform().os == PlatformOs.Ios) {
        LargeSpacer()
        SettingsItem(
            isChecked = uiState.enableIosNotifications,
            onClick = viewModel::setEnableIosNotifications,
            title = stringResource(Res.string.settings_enable_ios_notifications_title),
            subtitle = stringResource(Res.string.settings_enable_ios_notifications_subtitle)
        )
    }
}

@Composable
fun DeveloperSettings(
    uiState: SettingsUiState,
    viewModel: SettingsViewModel
) {
    LargeSpacer()
    SettingsTitle(
        text = stringResource(Res.string.settings_title_developer)
    )
    SettingsItem(
        isChecked = uiState.logAllNotificationActivity,
        onClick = viewModel::setLogAllNotificationActivity,
        title = stringResource(Res.string.settings_log_notification_activity_title),
    )
}

@Composable
fun AnimatedVisibilitySettingsItem(
    isVisible: Boolean,
    isChecked: Boolean,
    title: String,
    onClick: (Boolean) -> Unit,
    subtitle: String? = null,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.animateContentSize().fillMaxWidth()
    ) {
        if (isVisible) {
            SettingsItem(
                isChecked = isChecked,
                title = title,
                onClick = onClick,
                subtitle = subtitle,
                modifier = modifier
            )
        }
    }
}

@Composable
fun SettingsItem(
    isChecked: Boolean,
    title: String,
    onClick: (Boolean) -> Unit,
    subtitle: String? = null,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable(onClick = { onClick(!isChecked) }).padding(vertical = 4.dp)
    ) {
        LargeSpacer()
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            SmallSpacer()
            subtitle?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
        MediumSpacer()
        Switch(
            checked = isChecked,
            onCheckedChange = { onClick(!isChecked) },
        )
        LargeSpacer()
    }
}

@Composable
fun BuildInfo(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "BetterOrioks ${AppInfo.VERSION} for ${getPlatform().name}",
            modifier = modifier.clip(RoundedCornerShape(8.dp)).padding(horizontal = 2.dp).clickable(onClick = onClick),
            style = MaterialTheme.typography.labelSmall
        )
    }
}
