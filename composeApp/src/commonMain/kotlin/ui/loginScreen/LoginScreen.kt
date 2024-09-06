package ui.loginScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import betterorioks.composeapp.generated.resources.LogIn
import betterorioks.composeapp.generated.resources.Res
import model.login.LoginState
import org.jetbrains.compose.resources.stringResource
import ui.common.LoadingScreen
import ui.common.MediumSpacer

@Composable
fun topRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
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
        modifier = modifier
    ) {
        MediumSpacer()
        topRow(modifier = Modifier.padding(horizontal = 16.dp))
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
                modifier = Modifier.fillMaxSize()
            )
    }
}