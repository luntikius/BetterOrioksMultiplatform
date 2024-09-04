import database.getDatabaseBuilder
import org.koin.dsl.module

val iosModule = module(createdAtStart = true) {
    single { getDatabaseBuilder() }
}
