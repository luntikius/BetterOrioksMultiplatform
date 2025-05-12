package data.background

import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.notification_subject_subtitle
import betterorioks.composeapp.generated.resources.notification_subject_title
import data.NotificationsDatabaseRepository
import data.SubjectsWebRepository
import data.UserPreferencesRepository
import handlers.NotificationsHandler
import kotlinx.coroutines.flow.first
import model.BetterOrioksScreen
import model.background.BackgroundTask
import model.subjects.subjectsJson.SubjectsData
import org.jetbrains.compose.resources.getString

class SubjectNotificationsBackgroundTask(
    private val notificationsRepository: NotificationsDatabaseRepository,
    private val subjectsWebRepository: SubjectsWebRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val notificationsHandler: NotificationsHandler,
) : BackgroundTask {

    suspend fun executeWithData(
        subjects: SubjectsData,
        silently: Boolean
    ) {
        val notificationSubjects = subjects.toNotificationsSubjects()
        val diff = notificationsRepository.updateSubjectsAndGetDiff(notificationSubjects)
        diff.forEach {
            it.first.id
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
            if (!silently) {
                notificationsHandler.sendNotification(
                    title = title,
                    subtitle = subtitle,
                    screenOpenAction = BetterOrioksScreen.ControlEventsScreen(
                        subjectId = now.subjectId,
                        semesterId = null
                    )
                )
            }
        }
        if (userPreferencesRepository.settings.first().logAllNotificationActivity) {
            notificationsRepository.addNotification(
                title = "SubjectNotificationBackgroudTask",
                text = "task found ${diff.size} diff"
            )
        }
    }

    override suspend fun execute() {
        val authData = userPreferencesRepository.authData.first()
        val subjects = subjectsWebRepository.getSubjects(authData)
        executeWithData(subjects, false)
    }
}
