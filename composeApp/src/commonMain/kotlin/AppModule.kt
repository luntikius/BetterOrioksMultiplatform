import androidx.room.RoomDatabase
import data.ScheduleDatabaseRepository
import database.ScheduleDatabase
import org.koin.dsl.module

fun module() = module {

    single { ScheduleDatabase.getRoomDatabase(get<RoomDatabase.Builder<ScheduleDatabase>>()) }

    single { ScheduleDatabaseRepository(get()) }

}