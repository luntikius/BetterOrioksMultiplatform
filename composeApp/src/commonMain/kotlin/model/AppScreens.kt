package model

import data.OrioksWebRepository
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface BetterOrioksScreen {
    val name: String

    @Serializable
    data object ScheduleScreen : BetterOrioksScreen { override val name = "ScheduleScreen" }

    @Serializable
    data object SubjectsScreen : BetterOrioksScreen { override val name = "SubjectsScreen" }

    @Serializable
    data class ControlEventsScreen(
        val subjectId: String,
        val semesterId: String?,
    ) : BetterOrioksScreen { override val name = "ControlEventsScreen" }

    @Serializable
    data class ResourcesScreen(
        val subjectId: String,
        val scienceId: String,
        val subjectName: String
    ) : BetterOrioksScreen { override val name = "ResourcesScreen" }

    @Serializable
    data object MenuScreen : BetterOrioksScreen { override val name = "MenuScreen" }

    @Serializable
    data object SettingsScreen : BetterOrioksScreen { override val name = "SettingsScreen" }

    @Serializable
    data object NotificationsScreen : BetterOrioksScreen { override val name = "NotificationsScreen" }

    @Serializable
    data class NewsScreen(
        val subjectId: String?
    ) : BetterOrioksScreen { override val name = "NewsScreen" }

    @Serializable
    data class NewsViewScreen(
        val id: String,
        @SerialName("news_type")
        val type: String
    ) : BetterOrioksScreen {
        override val name = "NewsViewScreen"
        fun getType() = OrioksWebRepository.NewsType.valueOf(type)
    }
}