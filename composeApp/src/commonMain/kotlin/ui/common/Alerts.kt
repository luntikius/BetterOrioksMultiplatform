package ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.attention
import betterorioks.composeapp.generated.resources.cancel
import betterorioks.composeapp.generated.resources.switch_options_title
import model.schedule.SwitchOptions
import model.schedule.WeekType
import org.jetbrains.compose.resources.stringResource
import ui.scheduleScreen.ScheduleScreenUiState

@OptIn(ExperimentalMaterial3Api::class)
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
        BasicAlertDialog(
            onDismissRequest = onDismiss,
            modifier = modifier
        ) {
            Card(
                colors = CardDefaults.cardColors().copy(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                elevation = CardDefaults.cardElevation(0.dp),
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp)
                ) {
                    Text(
                        stringResource(Res.string.attention),
                        style = MaterialTheme.typography.headlineSmall
                    )
                    LargeSpacer()
                    Text(
                        text,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Start
                    )
                    LargeSpacer()
                    Row {
                        Spacer(Modifier.weight(1F))
                        TextButton(onClick = onActionButtonClick) {
                            Text(actionButtonText, color = MaterialTheme.colorScheme.error)
                        }
                        TextButton(onClick = onDismiss) {
                            Text(stringResource(Res.string.cancel))
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
        BasicAlertDialog(
            onDismissRequest = onDismiss,
            modifier = modifier
        ) {
            Card(
                colors = CardDefaults.cardColors().copy(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                elevation = CardDefaults.cardElevation(0.dp),
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp)
                ) {
                    Text(
                        stringResource(Res.string.switch_options_title),
                        style = MaterialTheme.typography.headlineSmall
                    )
                    LargeSpacer()
                    SwitchOptions.entries.forEach { switchOption ->
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
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                optionString,
                                textAlign = TextAlign.Center
                            )
                        }
                        SmallSpacer()
                    }
                    SmallSpacer()
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(onClick = onDismiss) {
                            Text(stringResource(Res.string.cancel))
                        }
                    }
                }
            }
        }
    }
}
