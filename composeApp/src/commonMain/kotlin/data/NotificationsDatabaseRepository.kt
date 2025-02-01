package data

import data.database.NotificationsDao
import model.database.notifications.NotificationsSubjectEntity

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
            val isDiff = savedSubject.maxScore != subject.maxScore ||
                savedSubject.currentScore != subject.currentScore
            if (isDiff) add(savedSubject to subject)
        }
        notificationsDao.dumpNotificationSubjects()
        notificationsDao.insertNotificationSubjects(notificationsSubjects)
    }

}