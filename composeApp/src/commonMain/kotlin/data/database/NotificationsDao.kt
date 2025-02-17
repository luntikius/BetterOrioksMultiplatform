package data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import model.database.notifications.NotificationEntity
import model.database.notifications.NotificationsNewsEntity
import model.database.notifications.NotificationsSubjectEntity

@Dao
interface NotificationsDao {

    @Insert
    suspend fun insertNotificationSubjects(list: List<NotificationsSubjectEntity>)

    @Insert
    suspend fun insertNotification(notification: NotificationEntity)

    @Insert
    suspend fun insertNotificationNews(notificationNews: NotificationsNewsEntity)

    @Query("SELECT * FROM notificationSubjects")
    suspend fun getNotificationSubjects(): List<NotificationsSubjectEntity>

    @Query("DELETE FROM notificationSubjects")
    suspend fun dumpNotificationSubjects()

    @Query("SELECT * FROM notifications")
    fun getNotificationsFlow(): Flow<List<NotificationEntity>>

    @Query("DELETE FROM notifications")
    suspend fun dumpNotifications()

    @Query("SELECT * FROM news LIMIT 1")
    suspend fun getNotificationNews(): NotificationsNewsEntity?

    @Query("DELETE FROM news")
    suspend fun dumpNotificationNews()
}
