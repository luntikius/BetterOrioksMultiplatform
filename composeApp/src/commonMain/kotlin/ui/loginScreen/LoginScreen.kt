package ui.loginScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import ui.common.LargeSpacer
import ui.common.LoadingScreen
import ui.common.MediumSpacer
import ui.common.XLargeSpacer
import ui.theme.gradientColor1
import ui.theme.gradientColor2
import ui.theme.gradientColor3

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

    val horizontalGradientBrush = Brush.horizontalGradient(
        colors = listOf(
            gradientColor1,
            gradientColor2,
            gradientColor3
        )
    )

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
    Button(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        contentPadding = PaddingValues(),
        onClick = { }
    ) {
        Box(
            modifier = Modifier
                .background(horizontalGradientBrush, shape = CircleShape)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ){
            Text(
                stringResource(Res.string.LogIn),
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Composable
fun LoginScreenContent(
    loginScreenViewModel: LoginScreenViewModel,
    modifier: Modifier = Modifier
) {
    val error = false
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        StaticLogo()
        LargeSpacer()
        if (error) {
            ErrorTextField()
        } else {
            LargeSpacer()
        }
        // Text(stringResource(Res.string.text_on_login), textAlign = TextAlign.Center)
        LoginInfoInput(loginScreenViewModel)
        Spacer(Modifier.weight(1f))
        TelegramIcon(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun ErrorTextField(){
    Text(
        text = "Неправильный логин или пароль",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.error
    )
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

@Composable
fun TelegramIcon(
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ){
        Icon(
            painter = painterResource(Res.drawable.telegram),
            contentDescription = "телеграм канал",
            modifier = Modifier.size(30.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Телеграм-канал",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
