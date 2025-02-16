package data.database

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime
import utils.BetterOrioksFormats

class OrioksConverter {

    @TypeConverter
    fun localDateTimeToString(l: LocalDateTime): String {
        return BetterOrioksFormats.NEWS_DATE_TIME_FORMAT.format(l)
    }

    @TypeConverter
    fun stringToLocalDateTime(s: String): LocalDateTime {
        return BetterOrioksFormats.NEWS_DATE_TIME_FORMAT.parse(s)
    }
}
