import database.getNotificationsDatabaseBuilder
import database.getScheduleDatabaseBuilder
import di.NOTIFICATIONS_DATABASE_BUILDER_NAME
import di.SCHEDULE_DATABASE_BUILDER_NAME
import handlers.BufferHandler
import handlers.ToastHandler
import handlers.UrlHandler
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun platformModule(): Module = module(createdAtStart = true) {
    single(named(SCHEDULE_DATABASE_BUILDER_NAME)) { getScheduleDatabaseBuilder(get()) }
    single(named(NOTIFICATIONS_DATABASE_BUILDER_NAME)) { getNotificationsDatabaseBuilder(get()) }
    single { createDataStore(get()) }
    single<UrlHandler> { AndroidUrlHandler(get()) }
    single<ToastHandler> { AndroidToastHandler(get()) }
    single<BufferHandler> { AndroidBufferHandler(get(), get()) }
}
