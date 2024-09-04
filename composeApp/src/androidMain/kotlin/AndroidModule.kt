import database.getDatabaseBuilder
import org.koin.dsl.module

val androidModule = module(createdAtStart = true) {
    single { getDatabaseBuilder(get()) }
}