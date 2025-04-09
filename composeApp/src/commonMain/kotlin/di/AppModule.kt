package di

import AppViewModel
import data.MietWebRepository
import data.NotificationsDatabaseRepository
import data.OrioksWebRepository
import data.ScheduleDatabaseRepository
import data.SubjectsRepository
import data.SubjectsWebRepository
import data.UserPreferencesRepository
import data.database.NotificationsDatabase
import data.database.ScheduleDatabase
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ui.controlEventsScreen.ControlEventsViewModel
import ui.loginScreen.LoginScreenViewModel
import ui.menuScreen.MenuScreenViewModel
import ui.newsScreen.NewsViewModel
import ui.newsScreen.newsViewScreen.NewsViewViewModel
import ui.notificationsScreen.NotificationsViewModel
import ui.resourcesScreen.ResourcesViewModel
import ui.scheduleScreen.ScheduleScreenViewModel
import ui.settingsScreen.SettingsViewModel
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
    single { SubjectsRepository(get(), get(), get()) }

    // db
    single { ScheduleDatabase.getRoomDatabase(get(named(SCHEDULE_DATABASE_BUILDER_NAME))) }
    single { NotificationsDatabase.getRoomDatabase(get(named(NOTIFICATIONS_DATABASE_BUILDER_NAME))) }
    single { get<ScheduleDatabase>().getDao() }
    single { get<NotificationsDatabase>().getDao() }
    single { ScheduleDatabaseRepository(get()) }
    single { NotificationsDatabaseRepository(get()) }
    single { UserPreferencesRepository(get()) }

    // view models
    factory { ScheduleScreenViewModel(get(), get(), get(), get(), get()) }
    factory { AppViewModel(get()) }
    factory { MenuScreenViewModel(get(), get(), get(), get()) }
    factory { LoginScreenViewModel(get(), get()) }
    factory { parameters -> NewsViewModel(parameters.getOrNull(String::class), get(), get(), get()) }
    factory { parameters -> NewsViewViewModel(parameters.get(), parameters.get(), get(), get()) }
    factory { SubjectsViewModel(get(), get()) }
    factory { parameters -> ControlEventsViewModel(parameters.get(), parameters.get(), get()) }
    factory { parameters -> ResourcesViewModel(parameters.get(), parameters.get(), get(), get()) }
    factory { NotificationsViewModel(get(), get(), get(), get(), get()) }
    factory { SettingsViewModel(get()) }
}

const val SCHEDULE_DATABASE_BUILDER_NAME = "schedule_database_builder"
const val NOTIFICATIONS_DATABASE_BUILDER_NAME = "notifications_database_builder"
