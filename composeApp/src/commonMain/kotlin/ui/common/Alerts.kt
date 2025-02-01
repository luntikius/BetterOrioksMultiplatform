package ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.attention
import betterorioks.composeapp.generated.resources.back_button
import betterorioks.composeapp.generated.resources.cancel
import betterorioks.composeapp.generated.resources.close
import betterorioks.composeapp.generated.resources.switch_options_title
import model.schedule.SwitchOptions
import model.schedule.WeekType
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.scheduleScreen.ScheduleScreenUiState

@Composable
fun AttentionAlert(
    isVisible: Boolean,
    text: String,
    actionButtonText: String,
    onAction: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val onActionButtonClick = {
        onAction()
        onDismiss()
    }
    if (isVisible) {
        BetterOrioksPopup(
            title = stringResource(Res.string.attention),
            onDismiss = onDismiss,
            modifier = modifier.fillMaxWidth(0.85f),
            columnModifier = Modifier.wrapContentHeight(),
            buttons = {
                TextButton(onClick = onActionButtonClick) {
                    Text(actionButtonText, color = MaterialTheme.colorScheme.error)
                }
                TextButton(onClick = onDismiss) {
                    Text(stringResource(Res.string.cancel))
                }
            }
        ) {
            item {
                Text(
                    text,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun SwitchAlert(
    isVisible: Boolean,
    scheduleScreenUiState: ScheduleScreenUiState,
    onSelectOption: (SwitchOptions) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val weekType = if (scheduleScreenUiState.selectedWeekIndex in scheduleScreenUiState.weeks.indices) {
        scheduleScreenUiState.weeks[scheduleScreenUiState.selectedWeekIndex].type
    } else {
        WeekType.FIRST_LOWER
    }

    if (isVisible) {
        BetterOrioksPopup(
            title = stringResource(Res.string.switch_options_title),
            onDismiss = onDismiss,
            modifier = modifier.fillMaxWidth(0.85f),
            columnModifier = Modifier.wrapContentHeight(),
            buttons = {
                TextButton(onClick = onDismiss) {
                    Text(stringResource(Res.string.cancel))
                }
            }
        ) {
            item { XLargeSpacer() }
            items(SwitchOptions.entries) { switchOption ->
                val optionString = when (switchOption) {
                    SwitchOptions.SWITCH_EVERY_WEEK -> {
                        stringResource(switchOption.nameRes)
                    }
                    SwitchOptions.SWITCH_EVERY_TWO_WEEKS -> {
                        stringResource(switchOption.nameRes, stringResource(weekType.weekName))
                    }
                    SwitchOptions.SWITCH_EVERY_FOUR_WEEKS -> {
                        stringResource(switchOption.nameRes, stringResource(weekType.stringRes))
                    }
                }
                OutlinedButton(
                    onClick = {
                        onSelectOption(switchOption)
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                ) {
                    Text(
                        optionString,
                        textAlign = TextAlign.Center
                    )
                }
                MediumSpacer()
            }
            item { XLargeSpacer() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BetterOrioksPopup(
    title: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    columnModifier: Modifier? = null,
    buttons: (@Composable () -> Unit)? = null,
    content: LazyListScope.() -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = { onDismiss() },
        modifier = modifier,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            colors = CardDefaults.cardColors().copy(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            elevation = CardDefaults.cardElevation(0.dp),
        ) {
            Column(
                modifier = Modifier.padding(top = 16.dp),
                horizontalAlignment = Alignment.End
            ) {
                Row(
                    modifier = Modifier.padding(start = 24.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(1f).padding(vertical = 4.dp)
                    )
                    if (buttons == null) {
                        IconButton(
                            onClick = onDismiss
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.close),
                                contentDescription = stringResource(Res.string.back_button),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
                MediumSpacer()
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                    modifier = (columnModifier ?: Modifier.weight(1f)).fillMaxWidth().padding(horizontal = 8.dp)
                ) {
                    LazyColumn(
                        content = content,
                        modifier = (columnModifier ?: Modifier.weight(1f)).fillMaxWidth()
                    )
                }
                SmallSpacer()
                buttons?.let {
                    Row(
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        it()
                    }
                }
                SmallSpacer()
            }
        }
    }
}
