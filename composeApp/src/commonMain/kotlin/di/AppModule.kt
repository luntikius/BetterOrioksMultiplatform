package di

import AppViewModel
import androidx.room.RoomDatabase
import data.MietWebRepository
import data.NotificationsDatabaseRepository
import data.OrioksWebRepository
import data.ScheduleDatabaseRepository
import data.SubjectsRepository
import data.SubjectsWebRepository
import data.UserPreferencesRepository
import data.database.NotificationsDatabase
import data.database.ScheduleDatabase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ui.controlEventsScreen.ControlEventsViewModel
import ui.loginScreen.LoginScreenViewModel
import ui.menuScreen.MenuScreenViewModel
import ui.newsScreen.NewsViewModel
import ui.newsScreen.newsViewScreen.NewsViewViewModel
import ui.resourcesScreen.ResourcesViewModel
import ui.scheduleScreen.ScheduleScreenViewModel
import ui.subjectsScreen.SubjectsViewModel
import utils.getDefaultHttpClient
import utils.getJson

fun sharedModule() = module {
    // web
    single { getDefaultHttpClient() }
    single { getJson() }
    single { MietWebRepository() }
    single { OrioksWebRepository(get(), get()) }
    single { SubjectsWebRepository(get(), get()) }
    single { SubjectsRepository(get(), get()) }

    // db
    single { ScheduleDatabase.getRoomDatabase(get<RoomDatabase.Builder<ScheduleDatabase>>()) }
    single { NotificationsDatabase.getRoomDatabase(get<RoomDatabase.Builder<NotificationsDatabase>>()) }
    single { get<ScheduleDatabase>().getDao() }
    single { get<NotificationsDatabase>().getDao() }
    single { ScheduleDatabaseRepository(get()) }
    single { NotificationsDatabaseRepository(get()) }
    single { UserPreferencesRepository(get()) }

    // view models
    viewModel { ScheduleScreenViewModel(get(), get(), get(), get()) }
    viewModel { AppViewModel(get()) }
    viewModel { MenuScreenViewModel(get(), get(), get()) }
    viewModel { LoginScreenViewModel(get(), get()) }
    viewModel { parameters -> NewsViewModel(parameters.getOrNull(String::class), get(), get()) }
    viewModel { parameters -> NewsViewViewModel(parameters.get(), parameters.get(), get(), get()) }
    viewModel { SubjectsViewModel(get(), get()) }
    viewModel { parameters -> ControlEventsViewModel(parameters.get(), get()) }
    viewModel { parameters -> ResourcesViewModel(parameters.get(), parameters.get(), get(), get()) }
}
