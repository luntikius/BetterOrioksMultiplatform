import androidx.compose.ui.window.ComposeUIViewController
import di.sharedModule
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController { App(null) }

fun startKoin() {
    startKoin {
        modules(platformModule(), backgroundModule(), sharedModule())
    }
}
