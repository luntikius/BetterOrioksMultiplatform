package di

import AppViewModel
import androidx.room.RoomDatabase
import data.MietWebRepository
import data.ScheduleDatabaseRepository
import data.UserPreferencesRepository
import data.database.OrioksWebRepository
import data.database.ScheduleDatabase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ui.loginScreen.LoginScreenViewModel
import ui.scheduleScreen.ScheduleScreenViewModel

fun sharedModule() = module {
    // web
    single { MietWebRepository() }

    // db
    single { ScheduleDatabase.getRoomDatabase(get<RoomDatabase.Builder<ScheduleDatabase>>()) }
    single { get<ScheduleDatabase>().getDao() }
    single { ScheduleDatabaseRepository(get()) }
    single { UserPreferencesRepository(get()) }
    single { OrioksWebRepository() }

    // view models
    single { ScheduleScreenViewModel(get(), get()) }
    single { AppViewModel(get()) }
    viewModel { LoginScreenViewModel(get(), get()) }
}
