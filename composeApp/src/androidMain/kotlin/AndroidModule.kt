import database.getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module

fun platformModule(): Module = module(createdAtStart = true) {
    single { getDatabaseBuilder(get()) }
    single { createDataStore(get()) }
}
