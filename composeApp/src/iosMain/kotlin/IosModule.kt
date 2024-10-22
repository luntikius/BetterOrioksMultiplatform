import database.getDatabaseBuilder
import org.koin.dsl.module
import utils.UrlHandler

fun platformModule() = module(createdAtStart = true) {
    single { getDatabaseBuilder() }
    single { createDataStore() }
    single<UrlHandler> { IosUrlHandler() }
}
