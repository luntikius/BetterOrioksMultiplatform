package model.database.notifications

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NotificationsNewsEntity(
    @PrimaryKey
    val id: String
)
