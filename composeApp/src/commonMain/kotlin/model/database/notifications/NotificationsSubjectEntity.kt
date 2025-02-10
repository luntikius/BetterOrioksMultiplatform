package model.database.notifications

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notificationSubjects")
data class NotificationsSubjectEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val subjectId: String,
    val subjectName: String,
    val controlEventName: String,
    val currentPoints: String,
    val maxPoints: String,
) {

    override fun equals(other: Any?): Boolean {
        return subjectName == (other as? NotificationsSubjectEntity)?.subjectName &&
            controlEventName == (other as? NotificationsSubjectEntity)?.controlEventName &&
            subjectId == (other as? NotificationsSubjectEntity)?.subjectId
    }
}
