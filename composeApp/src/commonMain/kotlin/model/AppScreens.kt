package model

import data.OrioksWebRepository
import kotlinx.serialization.Serializable

@Serializable
sealed interface BetterOrioksScreen {

    @Serializable
    data object ScheduleScreen : BetterOrioksScreen

    @Serializable
    data object SubjectsScreen : BetterOrioksScreen

    @Serializable
    data class ControlEventsScreen(
        val subjectId: String
    ) : BetterOrioksScreen

    @Serializable
    data class ResourcesScreen(
        val subjectId: String,
        val scienceId: String,
        val subjectName: String
    ) : BetterOrioksScreen

    @Serializable
    data object MenuScreen : BetterOrioksScreen

    @Serializable
    data object SettingsScreen : BetterOrioksScreen

    @Serializable
    data object NotificationsScreen : BetterOrioksScreen

    @Serializable
    data class NewsScreen(
        val subjectId: String?
    ) : BetterOrioksScreen

    @Serializable
    data class NewsViewScreen(
        val id: String,
        val type: String
    ) : BetterOrioksScreen {

        fun getType() = OrioksWebRepository.NewsType.valueOf(type)
    }

}