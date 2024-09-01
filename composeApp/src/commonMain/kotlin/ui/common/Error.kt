package ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.error_img
import betterorioks.composeapp.generated.resources.loading_failed
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ErrorScreen(text: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.error_img),
            contentDescription = null
        )
        LargeSpacer()
        Text(
            stringResource(Res.string.loading_failed),
            textAlign = TextAlign.Center
        )
        MediumSpacer()
        Text(
            text,
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center
        )

    }
}