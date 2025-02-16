package ui.notificationsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.academic_performance
import betterorioks.composeapp.generated.resources.checked_circle
import betterorioks.composeapp.generated.resources.content_description_clear
import betterorioks.composeapp.generated.resources.content_description_disabled
import betterorioks.composeapp.generated.resources.content_description_enabled
import betterorioks.composeapp.generated.resources.logo
import betterorioks.composeapp.generated.resources.news
import betterorioks.composeapp.generated.resources.news_notifications
import betterorioks.composeapp.generated.resources.no_notifications
import betterorioks.composeapp.generated.resources.notifications
import betterorioks.composeapp.generated.resources.notifications_history
import betterorioks.composeapp.generated.resources.notifications_settings
import betterorioks.composeapp.generated.resources.subjects_notifications
import betterorioks.composeapp.generated.resources.trash_can
import betterorioks.composeapp.generated.resources.unchecked_circle
import model.database.notifications.NotificationEntity
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import ui.common.DefaultHeader
import ui.common.EmptyItem
import ui.common.LargeSpacer
import ui.common.MediumSpacer
import ui.common.SmallSpacer
import ui.common.XLargeSpacer
import utils.BetterOrioksFormats

@Composable
fun NotificationsScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val viewModel: NotificationsViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        DefaultHeader(
            text = stringResource(Res.string.notifications),
            onBackButtonClick = navController::navigateUp,
        )
        LargeSpacer()
        Text(
            stringResource(Res.string.notifications_settings),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        LargeSpacer()
        NotificationButtonsRow(viewModel, uiState)
        XLargeSpacer()
        NotificationsHistoryTitle(
            viewModel::dumpNotificationsHistory,
        )
        MediumSpacer()
        NotificationsList(
            viewModel = viewModel,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun NotificationsHistoryTitle(
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            stringResource(Res.string.notifications_history),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
            modifier = Modifier.padding(horizontal = 16.dp).weight(1f)
        )
        IconButton(
            onClearClick
        ) {
            Icon(
                painter = painterResource(Res.drawable.trash_can),
                contentDescription = stringResource(Res.string.content_description_clear),
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun NotificationsList(
    viewModel: NotificationsViewModel,
    modifier: Modifier = Modifier,
) {
    val notifications by viewModel.notificationsHistoryFlow.collectAsState(listOf())
    if (notifications.isEmpty()) {
        EmptyItem(
            stringResource(Res.string.no_notifications),
            modifier = modifier.fillMaxSize()
        )
    } else {
        LazyColumn(
            modifier = modifier
        ) {
            item {
                LargeSpacer()
            }
            items(notifications) { notification ->
                NotificationItem(
                    notificationEntity = notification,
                    modifier = Modifier.fillParentMaxWidth()
                )
                SmallSpacer()
            }
            item {
                LargeSpacer()
            }
        }
    }
}

@Composable
fun NotificationItem(
    notificationEntity: NotificationEntity,
    modifier: Modifier = Modifier,
) = notificationEntity.run {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                LargeSpacer()
                Text(
                    "BetterOrioks • ${BetterOrioksFormats.NEWS_DATE_TIME_FORMAT.format(createdAt)}",
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Text(
                title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(start = 40.dp)
            )
            SmallSpacer()
            Text(
                text,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 40.dp)
            )
        }
    }
}

@Composable
fun NotificationButtonsRow(
    viewModel: NotificationsViewModel,
    uiState: NotificationsUiState
) {
    Row(
        modifier = Modifier.heightIn(min = 72.dp, max = 120.dp)
    ) {
        LargeSpacer()
        NotificationToggleButton(
            isChecked = uiState.notificationSettings.isSubjectNotificationEnabled,
            iconPainter = painterResource(Res.drawable.academic_performance),
            text = stringResource(Res.string.subjects_notifications),
            onClick = { viewModel.toggleSubjectNotifications() },
            modifier = Modifier.weight(1f)
        )
        LargeSpacer()
        NotificationToggleButton(
            isChecked = uiState.notificationSettings.isNewsNotificationsEnabled,
            iconPainter = painterResource(Res.drawable.news),
            text = stringResource(Res.string.news_notifications),
            onClick = { viewModel.toggleNewsNotifications() },
            modifier = Modifier.weight(1f)
        )
        LargeSpacer()
    }
}

@Composable
fun NotificationToggleButton(
    isChecked: Boolean,
    iconPainter: Painter,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (checkboxPainter, checkboxDescription, checkboxTint) = if (isChecked) {
        Triple(
            painterResource(Res.drawable.checked_circle),
            stringResource(Res.string.content_description_enabled),
            MaterialTheme.colorScheme.primary
        )
    } else {
        Triple(
            painterResource(Res.drawable.unchecked_circle),
            stringResource(Res.string.content_description_disabled),
            MaterialTheme.colorScheme.background
        )
    }

    Card(
        modifier = modifier,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(vertical = 16.dp).align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = iconPainter,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp),
                    tint = MaterialTheme.colorScheme.primary,
                )
                Text(text)
            }
            Icon(
                painter = checkboxPainter,
                contentDescription = checkboxDescription,
                tint = checkboxTint,
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = (-8).dp, y = 8.dp)
            )
        }
    }
}
