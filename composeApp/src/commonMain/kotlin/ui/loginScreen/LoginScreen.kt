package ui.loginScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import betterorioks.composeapp.generated.resources.LogIn
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.app_name
import betterorioks.composeapp.generated.resources.logo
import model.login.LoginState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
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
        Text(stringResource(Res.string.app_name), style = MaterialTheme.typography.displayMedium)
    }
}

@Composable
fun LoginInfoInput(
    loginScreenViewModel: LoginScreenViewModel,
    modifier: Modifier = Modifier
) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    OutlinedTextField(
        value = login,
        onValueChange = { login = it },
        modifier = Modifier.fillMaxWidth()
    )
    MediumSpacer()
    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        modifier = Modifier.fillMaxWidth()
    )
    XLargeSpacer()
    Button(
        onClick = { }
    ) {
        Text(
            stringResource(Res.string.LogIn),
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
fun LoginScreenContent(
    loginScreenViewModel: LoginScreenViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        StaticLogo()
        LargeSpacer()
        // Text(stringResource(Res.string.text_on_login), textAlign = TextAlign.Center)
        LargeSpacer()
        LoginInfoInput(loginScreenViewModel)
        Spacer(Modifier.weight(1f))
    }
}

@Composable
fun LoginScreen(
    navigate: () -> Unit,
    loginScreenViewModel: LoginScreenViewModel
) {
    val uiState by loginScreenViewModel.uiState.collectAsState()

    when (uiState.loginState) {
        is LoginState.ReLoading,
        is LoginState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
        is LoginState.Success -> navigate()
        is LoginState.LoginRequired ->
            LoginScreenContent(
                loginScreenViewModel = loginScreenViewModel,
                modifier = Modifier.fillMaxSize().padding(horizontal = 32.dp)
            )
    }
}
