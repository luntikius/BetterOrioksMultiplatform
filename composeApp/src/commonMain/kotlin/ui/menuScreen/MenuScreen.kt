package ui.menuScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.exit
import org.jetbrains.compose.resources.stringResource
import ui.common.CardButton

@Composable
fun MenuScreen(
    viewModel: MenuScreenViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        CardButton(
            text = stringResource(Res.string.exit),
            onClick = viewModel::logout,
            textColor = MaterialTheme.colorScheme.error,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
