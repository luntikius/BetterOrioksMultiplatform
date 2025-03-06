package ui.settingsScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.adaptive_mode
import betterorioks.composeapp.generated.resources.dark_mode
import betterorioks.composeapp.generated.resources.light_mode
import betterorioks.composeapp.generated.resources.settings
import betterorioks.composeapp.generated.resources.settings_theme_dark
import betterorioks.composeapp.generated.resources.settings_theme_light
import betterorioks.composeapp.generated.resources.settings_theme_system
import betterorioks.composeapp.generated.resources.theme
import model.settings.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import ui.common.DefaultHeader
import ui.common.LargeSpacer
import ui.common.MediumSpacer
import ui.common.ToggleButton

@Composable
fun SettingsScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<SettingsViewModel>()

    LazyColumn(
        modifier = modifier.padding(8.dp)
    ) {
        item {
            DefaultHeader(
                text = stringResource(Res.string.settings),
                onBackButtonClick = { navController.popBackStack() },
            )
            LargeSpacer()
            SettingsTitle(stringResource(Res.string.theme))
            ThemeButtons(viewModel)
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
}