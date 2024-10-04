package ui.loginScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import betterorioks.composeapp.generated.resources.LogIn
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.app_name
import betterorioks.composeapp.generated.resources.logo
import betterorioks.composeapp.generated.resources.telegram
import model.login.LoginState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ui.common.GradientButton
import ui.common.LargeSpacer
import ui.common.LoadingScreen
import ui.common.MediumSpacer
import ui.common.XLargeSpacer
import ui.theme.gradientColor1
import ui.theme.gradientColor2
import ui.theme.gradientColor3
import kotlin.math.log

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
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    OutlinedTextField(
        value = login,
        onValueChange = { login = it },
        shape = CircleShape,
        label = { Text(text = "Номер студенческого билета") },
        modifier = Modifier.fillMaxWidth()
    )
    MediumSpacer()
    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text(text = "Пароль") },
        shape = CircleShape,
        modifier = Modifier.fillMaxWidth()
    )
    XLargeSpacer()
    GradientButton(
        onClick = {  },
        enabled = login.isNotEmpty() && password.isNotEmpty(),
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
    loginState: LoginState
){
    val error = loginState is LoginState.LoginRequired
    if (error) {
        val errorReason =  (loginState as LoginState.LoginRequired).reason
        val textColor = if (errorReason.isUserError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground
        val text = stringResource(errorReason.stringRes)
        Text(
            text = text,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    } else {
        LargeSpacer()
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
