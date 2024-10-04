package ui.loginScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import betterorioks.composeapp.generated.resources.LogIn
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.app_name
import betterorioks.composeapp.generated.resources.login
import betterorioks.composeapp.generated.resources.logo
import betterorioks.composeapp.generated.resources.password
import betterorioks.composeapp.generated.resources.telegram
import model.login.LoginState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.common.GradientButton
import ui.common.LargeSpacer
import ui.common.LoadingScreen
import ui.common.MediumSpacer
import ui.common.XLargeSpacer

@Composable
fun StaticLogo(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(Res.drawable.logo),
            contentDescription = null,
            modifier = Modifier.size(128.dp)
        )
        Text(stringResource(Res.string.app_name), style = MaterialTheme.typography.displaySmall)
    }
}

@Composable
fun LoginInfoInput(
    loginScreenViewModel: LoginScreenViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by loginScreenViewModel.uiState.collectAsState()

    OutlinedTextField(
        value = uiState.login,
        onValueChange = loginScreenViewModel::setLogin,
        shape = CircleShape,
        label = { Text(text = stringResource(Res.string.login)) },
        modifier = Modifier.fillMaxWidth(),
        isError = uiState.isError,
        singleLine = true
    )
    MediumSpacer()
    OutlinedTextField(
        value = uiState.password,
        onValueChange = loginScreenViewModel::setPassword,
        label = { Text(text = stringResource(Res.string.password)) },
        shape = CircleShape,
        modifier = Modifier.fillMaxWidth(),
        isError = uiState.isError,
        singleLine = true
    )
    XLargeSpacer()
    GradientButton(
        text = stringResource(Res.string.LogIn),
        onClick = loginScreenViewModel::tryLogin,
        enabled = uiState.isButtonEnabled,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
    )
}

@Composable
fun LoginScreenContent(
    loginScreenViewModel: LoginScreenViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by loginScreenViewModel.uiState.collectAsState()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        StaticLogo()
        LargeSpacer()
        ErrorTextField(uiState.loginState)
        LoginInfoInput(loginScreenViewModel)
        Spacer(Modifier.weight(1f))
        TelegramIconButton(onClick = {})
    }
}

@Composable
fun ErrorTextField(
    loginState: LoginState,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier.animateContentSize()
    ) {
        val loginRequired = loginState is LoginState.LoginRequired
        if (loginRequired) {
            val errorReason = (loginState as LoginState.LoginRequired).reason
            val textColor = if (errorReason.isUserError) MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.onBackground
            val text = stringResource(errorReason.stringRes)
            Text(
                text = text,
                color = textColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        else {
            LargeSpacer()
        }
    }
}

@Composable
fun LoginScreen(
    navigate: () -> Unit,
    loginScreenViewModel: LoginScreenViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by loginScreenViewModel.uiState.collectAsState()

    when (uiState.loginState) {
        is LoginState.ReLoading,
        is LoginState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is LoginState.Success -> navigate()
        is LoginState.LoginRequired ->
            LoginScreenContent(
                loginScreenViewModel = loginScreenViewModel,
                modifier = modifier.fillMaxSize().padding(horizontal = 32.dp)
            )
    }
}

@Composable
fun TelegramIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(Res.drawable.telegram),
            contentDescription = null,
            modifier = Modifier.size(30.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
