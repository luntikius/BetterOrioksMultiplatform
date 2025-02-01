package ui.menuScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.exit
import betterorioks.composeapp.generated.resources.exit_alert_text
import betterorioks.composeapp.generated.resources.github
import betterorioks.composeapp.generated.resources.news
import betterorioks.composeapp.generated.resources.notifications
import betterorioks.composeapp.generated.resources.profile
import betterorioks.composeapp.generated.resources.settings
import betterorioks.composeapp.generated.resources.social_github
import betterorioks.composeapp.generated.resources.social_orioks
import betterorioks.composeapp.generated.resources.social_telegram
import betterorioks.composeapp.generated.resources.telegram
import betterorioks.composeapp.generated.resources.web
import model.NewsScreen
import model.user.UserInfo
import model.user.UserInfoState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.common.AttentionAlert
import ui.common.CardButton
import ui.common.DefaultPullToRefresh
import ui.common.ErrorScreenWithReloadButton
import ui.common.GradientIcon
import ui.common.HorizontalIconTextButton
import ui.common.LargeSpacer
import ui.common.LoadingScreen
import ui.common.SimpleIconButton
import ui.common.SmallSpacer
import ui.common.XLargeSpacer
import utils.UrlHandler
import utils.UsefulUrls

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
        textAlign = TextAlign.Center,
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
        modifier = modifier.animateContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState.userInfoState) {
            is UserInfoState.Loading ->
                LoadingScreen()
            is UserInfoState.Success ->
                UserInfoContent(userInfo = ((uiState.userInfoState as UserInfoState.Success).userInfo))
            is UserInfoState.Error ->
                ErrorScreenWithReloadButton(
                    (uiState.userInfoState as UserInfoState.Error).exception,
                    viewModel::getScreenData
                )
        }
    }
}

@Composable
fun NavigationItemsRow(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        SimpleIconButton(
            icon = painterResource(Res.drawable.notifications),
            text = stringResource(Res.string.notifications),
            onClick = { },
            enabled = false,
            modifier = Modifier.weight(1f)
        )
        SimpleIconButton(
            icon = painterResource(Res.drawable.news),
            text = stringResource(Res.string.news),
            onClick = { navController.navigate(NewsScreen(null)) },
            modifier = Modifier.weight(1f)
        )
        SimpleIconButton(
            icon = painterResource(Res.drawable.settings),
            text = stringResource(Res.string.settings),
            onClick = { },
            enabled = false,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun TextButtonColumn(
    urlHandler: UrlHandler = koinInject(),
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        HorizontalIconTextButton(
            painterResource(Res.drawable.web),
            text = stringResource(Res.string.social_orioks),
            onClick = { urlHandler.handleUrl(UsefulUrls.ORIOKS_URL) },
            modifier = Modifier.fillMaxWidth()
        )
        HorizontalIconTextButton(
            painterResource(Res.drawable.telegram),
            text = stringResource(Res.string.social_telegram),
            onClick = { urlHandler.handleUrl(UsefulUrls.TELEGRAM_URL) },
            modifier = Modifier.fillMaxWidth()
        )
        HorizontalIconTextButton(
            painterResource(Res.drawable.github),
            text = stringResource(Res.string.social_github),
            onClick = { urlHandler.handleUrl(UsefulUrls.GITHUB_URL) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    navController: NavController,
    viewModel: MenuScreenViewModel,
    modifier: Modifier = Modifier
) {
    var isExitAlertVisible by remember { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(Unit) {
        viewModel.getScreenData()
    }

    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            viewModel.getScreenData(true)
            pullToRefreshState.endRefresh()
        }
    }

    Box(
        modifier = modifier.nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        MenuScreenContent(
            viewModel = viewModel,
            navController = navController,
            onExitButtonClick = { isExitAlertVisible = true }
        )

        DefaultPullToRefresh(pullToRefreshState)

        AttentionAlert(
            isVisible = isExitAlertVisible,
            text = stringResource(Res.string.exit_alert_text),
            actionButtonText = stringResource(Res.string.exit),
            onAction = { viewModel.logout() },
            onDismiss = { isExitAlertVisible = false },
            modifier = Modifier.fillMaxWidth(0.7f)
        )
    }
}

@Composable
fun MenuScreenContent(
    viewModel: MenuScreenViewModel,
    navController: NavController,
    onExitButtonClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        item { UserInfoBlock(viewModel, Modifier.fillParentMaxWidth().padding(16.dp)) }
        item { LargeSpacer() }
        item { NavigationItemsRow(navController) }
        item { XLargeSpacer() }
        item { TextButtonColumn() }
        item { XLargeSpacer() }
        item {
            CardButton(
                text = stringResource(Res.string.exit),
                onClick = onExitButtonClick,
                textColor = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillParentMaxWidth()
            )
        }
        item { XLargeSpacer() }
    }
}
