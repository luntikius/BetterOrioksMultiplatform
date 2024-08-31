package model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "firstOfTheMonths")
data class FirstOfTheMonthEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String
)
