package ui.menuScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.exit
import model.user.UserInfo
import model.user.UserInfoState
import org.jetbrains.compose.resources.stringResource
import ui.common.CardButton
import ui.common.ErrorScreenWithReloadButton
import ui.common.LoadingScreen

@Composable
fun UserInfoContent(
    userInfo: UserInfo
) {
    Text(
        text = userInfo.name,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun UserInfoBlock(
    viewModel: MenuScreenViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState.userInfoState) {
            is UserInfoState.Loading ->
                LoadingScreen()
            is UserInfoState.Success ->
                UserInfoContent(userInfo = ((uiState.userInfoState as UserInfoState.Success).userInfo))
            is UserInfoState.Error ->
                ErrorScreenWithReloadButton(
                    (uiState.userInfoState as UserInfoState.Error).message,
                    viewModel::getScreenData
                )
        }
    }
}

@Composable
fun MenuScreen(
    viewModel: MenuScreenViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.getScreenData()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        item {
            UserInfoBlock(viewModel)
        }
        item {
            CardButton(
                text = stringResource(Res.string.exit),
                onClick = viewModel::logout,
                textColor = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
