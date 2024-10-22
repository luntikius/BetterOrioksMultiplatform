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
import ui.menuScreen.MenuScreenViewModel
import ui.newsScreen.NewsViewModel
import ui.newsScreen.newsViewScreen.NewsViewViewModel
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
    viewModel { ScheduleScreenViewModel(get(), get(), get(), get()) }
    viewModel { AppViewModel(get()) }
    viewModel { MenuScreenViewModel(get(), get(), get()) }
    viewModel { LoginScreenViewModel(get(), get()) }
    viewModel { NewsViewModel(get(), get()) }
    viewModel { parameters -> NewsViewViewModel(parameters.get(), get(), get()) }
}
