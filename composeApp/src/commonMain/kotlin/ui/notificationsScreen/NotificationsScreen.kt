package ui.notificationsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.content_description_clear
import betterorioks.composeapp.generated.resources.content_description_notifications_info
import betterorioks.composeapp.generated.resources.info
import betterorioks.composeapp.generated.resources.logo
import betterorioks.composeapp.generated.resources.news
import betterorioks.composeapp.generated.resources.news_notifications
import betterorioks.composeapp.generated.resources.no_notifications
import betterorioks.composeapp.generated.resources.notifications
import betterorioks.composeapp.generated.resources.notifications_history
import betterorioks.composeapp.generated.resources.notifications_info_pt1_subtitle
import betterorioks.composeapp.generated.resources.notifications_info_pt1_title
import betterorioks.composeapp.generated.resources.notifications_info_pt2_subtitle
import betterorioks.composeapp.generated.resources.notifications_info_pt2_title
import betterorioks.composeapp.generated.resources.notifications_info_title
import betterorioks.composeapp.generated.resources.notifications_settings
import betterorioks.composeapp.generated.resources.subjects
import betterorioks.composeapp.generated.resources.subjects_notifications
import betterorioks.composeapp.generated.resources.trash_can
import betterorioks.composeapp.generated.resources.web
import model.database.notifications.NotificationEntity
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import ui.common.BetterOrioksPopup
import ui.common.Bullet
import ui.common.DefaultHeader
import ui.common.EmptyItem
import ui.common.LargeSpacer
import ui.common.MediumSpacer
import ui.common.SmallSpacer
import ui.common.ToggleButton
import ui.common.XLargeSpacer
import utils.BetterOrioksFormats

@Composable
fun NotificationsScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val viewModel: NotificationsViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val notifications by viewModel.notificationsHistoryFlow.collectAsState(listOf())

    if (uiState.isInfoPopupVisible) {
        BetterOrioksPopup(
            title = stringResource(Res.string.notifications_info_title),
            onDismiss = { viewModel.setInfoPopupVisibility(false) },
            modifier = modifier.fillMaxWidth(0.85f),
            columnModifier = Modifier.wrapContentHeight(),
        ) {
            item {
                LargeSpacer()
                Bullet(
                    title = stringResource(Res.string.notifications_info_pt1_title),
                    subtitle = stringResource(Res.string.notifications_info_pt1_subtitle),
                    image = painterResource(Res.drawable.web),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                MediumSpacer()
                Bullet(
                    title = stringResource(Res.string.notifications_info_pt2_title),
                    subtitle = stringResource(Res.string.notifications_info_pt2_subtitle),
                    image = painterResource(Res.drawable.notifications),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                LargeSpacer()
            }
        }
    }

    Column {
        DefaultHeader(
            text = stringResource(Res.string.notifications),
            onBackButtonClick = navController::navigateUp,
            buttons = {
                Row {
                    IconButton(onClick = { viewModel.setInfoPopupVisibility(true) }, modifier = Modifier.size(40.dp)) {
                        Icon(
                            painter = painterResource(Res.drawable.info),
                            contentDescription = stringResource(Res.string.content_description_notifications_info),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    MediumSpacer()
                }
            }
        )
        MediumSpacer()
        LazyColumn(modifier = modifier.fillMaxSize()) {
            item {
                MediumSpacer()
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
            }
            if (notifications.isEmpty()) {
                item {
                    EmptyItem(
                        stringResource(Res.string.no_notifications),
                        modifier = modifier.fillParentMaxWidth()
                    )
                }
            } else {
                item {
                    LargeSpacer()
                }
                items(notifications) { notification ->
                    NotificationItem(
                        notificationEntity = notification,
                        modifier = Modifier.fillParentMaxWidth().padding(horizontal = 16.dp)
                    )
                    SmallSpacer()
                }
                item {
                    LargeSpacer()
                }
            }
        }
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
        ToggleButton(
            isChecked = uiState.notificationSettings.isSubjectNotificationEnabled,
            iconPainter = painterResource(Res.drawable.subjects),
            text = stringResource(Res.string.subjects_notifications),
            onClick = { viewModel.toggleSubjectNotifications() },
            modifier = Modifier.weight(1f)
        )
        LargeSpacer()
        ToggleButton(
            isChecked = uiState.notificationSettings.isNewsNotificationsEnabled,
            iconPainter = painterResource(Res.drawable.news),
            text = stringResource(Res.string.news_notifications),
            onClick = { viewModel.toggleNewsNotifications() },
            modifier = Modifier.weight(1f)
        )
        LargeSpacer()
    }
}
