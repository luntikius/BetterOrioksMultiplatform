package ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import betterorioks.composeapp.generated.resources.Refresh
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.attention
import betterorioks.composeapp.generated.resources.cancel
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefreshAlert(
    isVisible: Boolean,
    text: String,
    onRefresh: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val onRefreshButtonClick = {
        onRefresh()
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
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp)
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
                        TextButton(onClick = onRefreshButtonClick) {
                            Text(stringResource(Res.string.Refresh))
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