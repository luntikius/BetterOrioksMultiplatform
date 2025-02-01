package model

import data.OrioksWebRepository
import kotlinx.serialization.Serializable

@Serializable
data object ScheduleScreen

@Serializable
data object SubjectsScreen

@Serializable
data class ControlEventsScreen(
    val subjectId: String
)

@Serializable
data class ResourcesScreen(
    val subjectId: String,
    val scienceId: String,
    val subjectName: String
)

@Serializable
data object MenuScreen

//    SettingsScreen,
//    NotificationsScreen,
@Serializable
data class NewsScreen(
    val subjectId: String?
)

@Serializable
data class NewsViewScreen(
    val id: String,
    val type: String
) {
    fun getType() = OrioksWebRepository.NewsType.valueOf(type)
}
