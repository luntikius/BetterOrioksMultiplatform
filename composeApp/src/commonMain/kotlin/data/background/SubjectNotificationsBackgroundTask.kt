package data.background

import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.notification_subject_subtitle
import betterorioks.composeapp.generated.resources.notification_subject_title
import data.NotificationsDatabaseRepository
import data.SubjectsWebRepository
import data.UserPreferencesRepository
import handlers.NotificationsHandler
import kotlinx.coroutines.flow.first
import model.background.BackgroundTask
import org.jetbrains.compose.resources.getString

class SubjectNotificationsBackgroundTask(
    private val notificationsRepository: NotificationsDatabaseRepository,
    private val subjectsWebRepository: SubjectsWebRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val notificationsHandler: NotificationsHandler,
) : BackgroundTask {

    override suspend fun execute() {
        val authData = userPreferencesRepository.authData.first()
        val subjects = subjectsWebRepository.getSubjects(authData)
        val notificationSubjects = subjects.toNotificationsSubjects()
        val diff = notificationsRepository.updateSubjectsAndGetDiff(notificationSubjects)
        diff.forEach {
            val was = it.first
            val now = it.second
            val title = getString(Res.string.notification_subject_title, was.subjectName)
            val subtitle = getString(
                Res.string.notification_subject_subtitle,
                was.controlEventName,
                was.currentPoints,
                was.maxPoints,
                now.currentPoints,
                now.maxPoints
            )
            notificationsRepository.addNotification(title = title, text = subtitle)
            notificationsHandler.sendNotification(
                title = title,
                subtitle = subtitle
            )
        }
    }
}
