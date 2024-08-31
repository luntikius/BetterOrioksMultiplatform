import androidx.compose.runtime.Composable
import database.ScheduleDao
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.theme.BetterOrioksTheme

@Composable
@Preview
fun App(scheduleDao: ScheduleDao) {
    BetterOrioksTheme {
        BetterOrioksApp(scheduleDao)
    }
}
