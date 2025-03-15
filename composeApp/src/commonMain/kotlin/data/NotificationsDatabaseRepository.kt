package data

import data.database.NotificationsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import model.database.notifications.NotificationEntity
import model.database.notifications.NotificationsNewsEntity
import model.database.notifications.NotificationsSubjectEntity
import utils.now

class NotificationsDatabaseRepository(
    private val notificationsDao: NotificationsDao
) {

    suspend fun updateSubjectsAndGetDiff(
        notificationsSubjects: List<NotificationsSubjectEntity>
    ): List<Pair<NotificationsSubjectEntity, NotificationsSubjectEntity>> = buildList {
        val savedNotificationsSubjects = notificationsDao.getNotificationSubjects()
        notificationsSubjects.forEach { subject ->
            val savedSubject = savedNotificationsSubjects.firstOrNull { savedSubject -> savedSubject == subject }
            if (savedSubject == null) return@forEach
            val isDiff = savedSubject.maxPoints != subject.maxPoints ||
                savedSubject.currentPoints != subject.currentPoints
            if (isDiff) add(savedSubject to subject)
        }
        notificationsDao.dumpNotificationSubjects()
        notificationsDao.insertNotificationSubjects(notificationsSubjects)
    }

    suspend fun updateNewsAndGetDiff(
        newsId: String
    ): Boolean {
        val savedNewsId = notificationsDao.getNotificationNews()?.id
        notificationsDao.dumpNotificationNews()
        notificationsDao.insertNotificationNews(NotificationsNewsEntity(newsId))
        if (savedNewsId == null) return false
        return newsId != savedNewsId
    }

    suspend fun addNotification(
        title: String,
        text: String
    ) {
        notificationsDao.insertNotification(
            NotificationEntity(
                title = title,
                text = text,
                createdAt = now()
            )
        )
    }

    fun getNotificationsFlow(): Flow<List<NotificationEntity>> =
        notificationsDao.getNotificationsFlow().map { list -> list.sortedByDescending { it.createdAt } }

    suspend fun dumpNotificationsHistory() {
        notificationsDao.dumpNotifications()
    }

    suspend fun dumpAll() {
        notificationsDao.dumpNotifications()
        notificationsDao.dumpNotificationSubjects()
        notificationsDao.dumpNotificationNews()
    }
}
