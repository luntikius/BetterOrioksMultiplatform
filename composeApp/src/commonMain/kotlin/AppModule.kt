import androidx.room.RoomDatabase
import data.MietWebRepository
import data.ScheduleDatabaseRepository
import database.ScheduleDatabase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ui.scheduleScreen.ScheduleScreenViewModel

fun sharedModule() = module {
    // web
    single { MietWebRepository() }

    // db
    single { ScheduleDatabase.getRoomDatabase(get<RoomDatabase.Builder<ScheduleDatabase>>()) }
    single { get<ScheduleDatabase>().getDao() }
    single { ScheduleDatabaseRepository(get()) }

    // view models
    viewModel { ScheduleScreenViewModel(get(), get()) }
}
