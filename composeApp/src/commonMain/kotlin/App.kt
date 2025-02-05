import androidx.compose.runtime.Composable
import androidx.compose.ui.text.intl.Locale
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import ui.theme.BetterOrioksTheme

@Composable
fun getLanguage() = Locale("ru")

@Composable
@Preview
fun App() {
    KoinContext {
        BetterOrioksTheme {
            BetterOrioksApp(koinViewModel())
        }
    }
}
