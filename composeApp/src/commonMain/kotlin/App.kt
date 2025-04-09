import androidx.compose.runtime.Composable
import model.BetterOrioksScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
@Preview
fun App(
    openScreenAction: BetterOrioksScreen?
) {
    KoinContext {
        BetterOrioksApp(
            appViewModel = koinInject(),
            openScreenAction = openScreenAction
        )
    }
}
