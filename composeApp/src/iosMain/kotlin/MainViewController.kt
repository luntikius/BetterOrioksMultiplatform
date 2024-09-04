import androidx.compose.ui.window.ComposeUIViewController
import database.ScheduleDatabase
import database.getDatabaseBuilder
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController {
    val databaseBuilder = getDatabaseBuilder()
    val database = ScheduleDatabase.getRoomDatabase(databaseBuilder)
    App(database.getDao())
}

fun initKoinIos() {
    startKoin {
        modules(iosModule, module())
    }
}
