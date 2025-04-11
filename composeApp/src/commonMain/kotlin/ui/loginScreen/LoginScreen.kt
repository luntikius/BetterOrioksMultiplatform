package ui.loginScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import betterorioks.composeapp.generated.resources.LogIn
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.app_name
import betterorioks.composeapp.generated.resources.login
import betterorioks.composeapp.generated.resources.logo
import betterorioks.composeapp.generated.resources.password
import betterorioks.composeapp.generated.resources.telegram_filled
import betterorioks.composeapp.generated.resources.visibility
import betterorioks.composeapp.generated.resources.visibility_off
import betterorioks.composeapp.generated.resources.visibility_on
import handlers.UrlHandler
import model.login.LoginState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.common.GradientButton
import ui.common.LargeSpacer
import ui.common.LoadingScreen
import ui.common.MediumSpacer
import ui.common.XLargeSpacer
import utils.UsefulUrls

@Composable
fun StaticLogo(
    showSubtitle: Boolean = true,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(Res.drawable.logo),
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )
        if (showSubtitle) Text(stringResource(Res.string.app_name), style = MaterialTheme.typography.displaySmall)
    }
}

@Composable
fun LoginInfoInput(
    loginScreenViewModel: LoginScreenViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by loginScreenViewModel.uiState.collectAsState()
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = uiState.login,
            onValueChange = loginScreenViewModel::setLogin,
            shape = CircleShape,
            label = { Text(text = stringResource(Res.string.login)) },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.isError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.Next
            )
        )
        MediumSpacer()
        OutlinedTextField(
            value = uiState.password,
            onValueChange = loginScreenViewModel::setPassword,
            label = { Text(text = stringResource(Res.string.password)) },
            shape = CircleShape,
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.isError,
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            trailingIcon = {
                val icon = if (passwordVisible) Res.drawable.visibility_on else Res.drawable.visibility_off
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = stringResource(Res.string.visibility),
                        tint = if (uiState.isError) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        },
                        modifier = Modifier
                            .padding(4.dp)
                            .size(24.dp)
                    )
                }
            }
        )
        XLargeSpacer()
        GradientButton(
            text = stringResource(Res.string.LogIn),
            onClick = loginScreenViewModel::tryLogin,
            enabled = uiState.isButtonEnabled,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
        )
    }
}

@Composable
fun LoginScreenContent(
    loginScreenViewModel: LoginScreenViewModel,
    urlHandler: UrlHandler = koinInject(),
    modifier: Modifier = Modifier
) {
    val uiState by loginScreenViewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(1f))
            StaticLogo()
            ErrorText(uiState.loginState)
            LoginInfoInput(loginScreenViewModel)
            Spacer(Modifier.weight(1f))
        }
        TelegramIconButton(onClick = { urlHandler.handleUrl(UsefulUrls.TELEGRAM_URL) })
    }
}

@Composable
fun ErrorText(
    loginState: LoginState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.animateContentSize()
    ) {
        val loginRequired = loginState is LoginState.LoginRequired
        if (loginRequired) {
            val errorReason = (loginState as LoginState.LoginRequired).reason
            val textColor = if (errorReason.isUserError) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.onBackground
            }
            val text = stringResource(errorReason.stringRes)
            LargeSpacer()
            Text(
                text = text,
                color = textColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            MediumSpacer()
        } else {
            XLargeSpacer()
        }
    }
}

@Composable
fun LoginScreen(
    loginScreenViewModel: LoginScreenViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by loginScreenViewModel.uiState.collectAsState()

    when (uiState.loginState) {
        is LoginState.Success -> {}
        is LoginState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is LoginState.LoginRequired ->
            LoginScreenContent(
                loginScreenViewModel = loginScreenViewModel,
                modifier = modifier.fillMaxSize().padding(horizontal = 32.dp)
            )
        is LoginState.IntroductionRequired ->
            IntroductionContent(
                loginScreenViewModel,
            )
    }
}

@Composable
fun IntroductionContent(
    loginScreenViewModel: LoginScreenViewModel
) {
    val uiState by loginScreenViewModel.uiState.collectAsState()
    Box(
        modifier = Modifier.padding(32.dp)
    ) {
        AnimatedVisibility(
            visible = uiState.introductionSlide == 0,
            enter = slideInHorizontally { it },
            exit = slideOutHorizontally { -it }
        ) {
            Slide1(loginScreenViewModel::onIntroductionNextClick)
        }
        AnimatedVisibility(
            visible = uiState.introductionSlide == 1,
            enter = slideInHorizontally { it },
            exit = slideOutHorizontally { -it }
        ) {
            Slide2(loginScreenViewModel::onIntroductionNextClick)
        }
        AnimatedVisibility(
            visible = uiState.introductionSlide == 2,
            enter = slideInHorizontally { it },
            exit = slideOutHorizontally { -it }
        ) {
            Slide3(loginScreenViewModel::onIntroductionNextClick)
        }
    }
}

@Composable
fun TelegramIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(Res.drawable.telegram_filled),
            contentDescription = null,
            modifier = Modifier.size(30.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
