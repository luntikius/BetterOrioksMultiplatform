import androidx.compose.ui.window.ComposeUIViewController
import database.ScheduleDatabase
import database.getDatabaseBuilder

fun MainViewController() = ComposeUIViewController {
    val databaseBuilder = getDatabaseBuilder()
    val database = ScheduleDatabase.getRoomDatabase(databaseBuilder)
    App(database.getDao())
}
