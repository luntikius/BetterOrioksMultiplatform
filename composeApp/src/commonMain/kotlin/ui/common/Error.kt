package ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.done
import betterorioks.composeapp.generated.resources.incomplete_poll_button
import betterorioks.composeapp.generated.resources.incomplete_poll_completed
import betterorioks.composeapp.generated.resources.incomplete_poll_subtitle
import betterorioks.composeapp.generated.resources.incomplete_poll_text
import betterorioks.composeapp.generated.resources.loading_failed
import betterorioks.composeapp.generated.resources.reload
import betterorioks.composeapp.generated.resources.warning
import handlers.BufferHandler
import handlers.UrlHandler
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import utils.RedirectToPollException

@Composable
@Suppress("UseIfInsteadOfWhen")
fun ErrorScreenWithReloadButton(
    exception: Exception,
    onClick: () -> Unit,
    urlHandler: UrlHandler = koinInject(),
    bufferHandler: BufferHandler = koinInject(),
    modifier: Modifier = Modifier,
) {
    when (exception) {
        is RedirectToPollException -> {
            PollErrorScreenWithReloadButton(
                exception,
                onClick,
                urlHandler,
                modifier
            )
        }
        else -> {
            BaseErrorScreenWithReloadButton(
                exception,
                onClick,
                bufferHandler,
                modifier
            )
        }
    }
}

@Composable
private fun PollErrorScreenWithReloadButton(
    exception: RedirectToPollException,
    onClick: () -> Unit,
    urlHandler: UrlHandler,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.done),
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )
        LargeSpacer()
        Text(
            stringResource(Res.string.incomplete_poll_text),
            textAlign = TextAlign.Center
        )
        MediumSpacer()
        Text(
            stringResource(Res.string.incomplete_poll_subtitle),
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center
        )
        LargeSpacer()
        Button(onClick = { urlHandler.handleUrl(exception.url) }) {
            Text(stringResource(Res.string.incomplete_poll_button))
        }
        SmallSpacer()
        TextButton(onClick = onClick) {
            Text(stringResource(Res.string.incomplete_poll_completed))
        }
    }
}

@Composable
private fun BaseErrorScreenWithReloadButton(
    exception: Exception,
    onClick: () -> Unit,
    bufferHandler: BufferHandler,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.warning),
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )
        LargeSpacer()
        Text(
            stringResource(Res.string.loading_failed),
            textAlign = TextAlign.Center
        )
        MediumSpacer()
        LazyRow(
            modifier = Modifier.clickable {
                bufferHandler.copyToClipboard(
                    text = exception.message.toString(),
                    label = "better orioks error message"
                )
            }
        ) {
            item {
                Text(
                    exception.message.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }
        LargeSpacer()
        Button(onClick = onClick) {
            Text(stringResource(Res.string.reload))
        }
    }
}
