import database.getNotificationsDatabaseBuilder
import database.getScheduleDatabaseBuilder
import di.NOTIFICATIONS_DATABASE_BUILDER_NAME
import di.SCHEDULE_DATABASE_BUILDER_NAME
import handlers.BufferHandler
import handlers.IosBufferHandler
import handlers.IosNotificationsHandler
import handlers.IosPermissionRequestHandler
import handlers.IosToastHandler
import handlers.IosUrlHandler
import handlers.NotificationsHandler
import handlers.PermissionRequestHandler
import handlers.ToastHandler
import handlers.UrlHandler
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun platformModule() = module(createdAtStart = true) {
    single(named(SCHEDULE_DATABASE_BUILDER_NAME)) { getScheduleDatabaseBuilder() }
    single(named(NOTIFICATIONS_DATABASE_BUILDER_NAME)) { getNotificationsDatabaseBuilder() }
    single { createDataStore() }
    single<UrlHandler> { IosUrlHandler() }
    single<ToastHandler> { IosToastHandler() }
    single<BufferHandler> { IosBufferHandler(get()) }
    single<NotificationsHandler> { IosNotificationsHandler() }
    single<PermissionRequestHandler> { IosPermissionRequestHandler() }
}
