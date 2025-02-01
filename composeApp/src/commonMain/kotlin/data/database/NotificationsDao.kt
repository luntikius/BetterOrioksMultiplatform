package data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import model.database.notifications.NotificationsSubjectEntity

@Dao
interface NotificationsDao {

    @Insert
    suspend fun insertNotificationSubjects(list: List<NotificationsSubjectEntity>)

    @Query("SELECT * FROM notificationSubjects")
    suspend fun getNotificationSubjects(): List<NotificationsSubjectEntity>

    @Query("DELETE FROM notificationSubjects")
    suspend fun dumpNotificationSubjects()

}