import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import ui.theme.BetterOrioksTheme

@Composable
@Preview
fun App() {
    KoinContext {
        BetterOrioksTheme {
            BetterOrioksApp(koinViewModel())
        }
    }
}
