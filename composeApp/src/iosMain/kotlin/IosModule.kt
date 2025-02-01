import database.getScheduleDatabaseBuilder
import org.koin.dsl.module
import utils.BufferHandler
import utils.UrlHandler

fun platformModule() = module(createdAtStart = true) {
    single { getScheduleDatabaseBuilder() }
    single { createDataStore() }
    single<UrlHandler> { IosUrlHandler() }
    single<BufferHandler> { IosBufferHandler() }
}
