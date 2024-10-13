package ui.menuScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import betterorioks.composeapp.generated.resources.news
import betterorioks.composeapp.generated.resources.notifications
import betterorioks.composeapp.generated.resources.profile
import betterorioks.composeapp.generated.resources.settings
import model.user.UserInfo
import model.user.UserInfoState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.common.CardButton
import ui.common.ErrorScreenWithReloadButton
import ui.common.GradientIcon
import ui.common.LargeSpacer
import ui.common.LoadingScreen
import ui.common.SimpleIconButton
import ui.common.SmallSpacer
import ui.common.XLargeSpacer

@Composable
fun UserInfoContent(
    userInfo: UserInfo
) {
    GradientIcon(
        painter = painterResource(Res.drawable.profile),
        modifier = Modifier.size(80.dp)
    )
    SmallSpacer()
    Text(
        text = userInfo.name,
        style = MaterialTheme.typography.titleLarge
    )
    SmallSpacer()
    Text(
        text = "${userInfo.login} · ${userInfo.group}",
        style = MaterialTheme.typography.labelSmall
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
fun NavigationItemsRow(
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        SimpleIconButton(
            icon = painterResource(Res.drawable.notifications),
            text = stringResource(Res.string.notifications),
            onClick = {},
            modifier = Modifier.weight(1f)
        )
        SimpleIconButton(
            icon = painterResource(Res.drawable.news),
            text = stringResource(Res.string.news),
            onClick = {},
            modifier = Modifier.weight(1f)
        )
        SimpleIconButton(
            icon = painterResource(Res.drawable.settings),
            text = stringResource(Res.string.settings),
            onClick = {},
            modifier = Modifier.weight(1f)
        )
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
            UserInfoBlock(viewModel, Modifier.fillParentMaxWidth().padding(16.dp))
            LargeSpacer()
        }
        item {
            NavigationItemsRow()
        }
        item { repeat(10) { XLargeSpacer() } }
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
