import database.getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module
import utils.UrlHandler

fun platformModule(): Module = module(createdAtStart = true) {
    single { getDatabaseBuilder(get()) }
    single { createDataStore(get()) }
    single<UrlHandler> { AndroidUrlHandler(get()) }
}
