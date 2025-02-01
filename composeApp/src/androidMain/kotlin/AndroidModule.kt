import database.getNotificationsDatabaseBuilder
import database.getScheduleDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module
import utils.BufferHandler
import utils.UrlHandler

fun platformModule(): Module = module(createdAtStart = true) {
    single { getScheduleDatabaseBuilder(get()) }
    single { getNotificationsDatabaseBuilder(get()) }
    single { createDataStore(get()) }
    single<UrlHandler> { AndroidUrlHandler(get()) }
    single<BufferHandler> { AndroidBufferHandler(get()) }
}
