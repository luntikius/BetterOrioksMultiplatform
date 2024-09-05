import database.getDatabaseBuilder
import org.koin.dsl.module

fun platformModule() = module(createdAtStart = true) {
    single { getDatabaseBuilder() }
}
