import androidx.activity.ComponentActivity
import data.background.NewsNotificationsBackgroundTask
import data.background.SubjectNotificationsBackgroundTask
import database.getNotificationsDatabaseBuilder
import database.getScheduleDatabaseBuilder
import di.NOTIFICATIONS_DATABASE_BUILDER_NAME
import di.SCHEDULE_DATABASE_BUILDER_NAME
import handlers.AndroidBackgroundHandler
import handlers.AndroidBufferHandler
import handlers.AndroidNotificationsHandler
import handlers.AndroidPermissionRequestHandler
import handlers.AndroidToastHandler
import handlers.AndroidUrlHandler
import handlers.BackgroundHandler
import handlers.BufferHandler
import handlers.NotificationsHandler
import handlers.PermissionRequestHandler
import handlers.ToastHandler
import handlers.UrlHandler
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun platformModule(): Module = module(createdAtStart = true) {
    single(named(SCHEDULE_DATABASE_BUILDER_NAME)) { getScheduleDatabaseBuilder(get()) }
    single(named(NOTIFICATIONS_DATABASE_BUILDER_NAME)) { getNotificationsDatabaseBuilder(get()) }
    single { createDataStore(get()) }
    single<UrlHandler> { AndroidUrlHandler(get(), get()) }
    single<ToastHandler> { AndroidToastHandler(get()) }
    single<BufferHandler> { AndroidBufferHandler(get(), get()) }
    single<NotificationsHandler> { AndroidNotificationsHandler(get()) }
    single<BackgroundHandler> { AndroidBackgroundHandler(get()) }
}

fun activityModule(activity: ComponentActivity): Module = module(createdAtStart = true) {
    single<PermissionRequestHandler>(createdAtStart = true) { AndroidPermissionRequestHandler(activity) }
}

fun backgroundModule(): Module = module {

    single { SubjectNotificationsBackgroundTask(get(), get(), get(), get()) }

    single { NewsNotificationsBackgroundTask(get(), get(), get(), get()) }
}
