package model.subjects.subjectsJson

import model.database.notifications.NotificationsSubjectEntity
import model.schedule.SemesterDates
import model.schedule.scheduleJson.Semester
import model.subjectPerformance.ControlEventsListItem
import model.subjectPerformance.DisplaySubjectPerformance
import model.subjects.SubjectListItem
import model.subjects.subjectsJson.jsonElements.ControlEvent
import model.subjects.subjectsJson.jsonElements.SubjectFromWeb

interface SubjectsData {
    val subjects: List<SubjectFromWeb>
    val offsetSubjects: List<SubjectFromWeb>
    val debts: List<SubjectFromWeb>
    val semesters: List<Semester>
    val subjectsWithMoodleIds: List<String> // Id subject'ов, у которых есть курс moodle

    val currentSubjects: List<SubjectFromWeb>
        get() = subjects + offsetSubjects

    val subjectListItems: List<SubjectListItem>
        get() = currentSubjects.map {
            it.toSubjectListItem(subjectsWithMoodleIds)
        }

    val debtSubjectListItems: List<SubjectListItem>
        get() = debts.map {
            it.toSubjectListItem(subjectsWithMoodleIds)
        }

    val allSubjects: List<SubjectFromWeb>
        get() = currentSubjects + debts

    fun getDisplaySubjectPerformance(shouldAddWeeksLeftItem: Boolean): Map<String, DisplaySubjectPerformance> = buildMap {
        allSubjects.forEach { subject ->
            val id = subject.id.toString() + if (subject.isDebt) DEBT_POSTFIX else NO_DEBT_POSTFIX
            val weeksPassedSinceSemesterStart = getLastSemesterDates().weeksPassedSinceSemesterStart
            val controlEventsList = buildControlEventsList(
                subject.controlEvents,
                weeksPassedSinceSemesterStart,
                subject.formOfControl.name,
                shouldAddWeeksLeftItem
            )
            val displaySubjectPerformance = DisplaySubjectPerformance(
                subject = subject.toSubjectListItem(subjectsWithMoodleIds),
                controlEvents = controlEventsList,
                controlForm = subject.formOfControl.name.ifBlank { null },
                teachers = subject.teachers,
            )
            put(
                id, displaySubjectPerformance
            )
        }
    }

    fun getLastSemesterDates(): SemesterDates {
        return semesters.last { it.startDate != null }.toSemesterDates()
    }

    private fun buildControlEventsList(
        controlEvents: List<ControlEvent>,
        weeksPassedSinceSemesterStart: Int,
        controlForm: String,
        shouldAddWeeksLeftItem: Boolean,
    ): List<ControlEventsListItem> = buildSet {
        controlEvents.forEach { controlEvent ->
            val weeksBeforeEvent = maxOf(-1, controlEvent.week - weeksPassedSinceSemesterStart - 1)
            val weeksLeftItem = ControlEventsListItem.WeeksLeftItem(weeksBeforeEvent)
            if (!this.contains(weeksLeftItem) && shouldAddWeeksLeftItem) add(weeksLeftItem)
            add(controlEvent.toControlEventItem(controlForm))
        }
    }.toList()

    fun toNotificationsSubjects(): List<NotificationsSubjectEntity> = buildList {
        allSubjects.forEach { subject ->
            subject.controlEvents.forEach { controlEventFromWeb ->
                val controlEvent = controlEventFromWeb.toControlEventItem(controlForm = subject.formOfControl.name)
                add(
                    NotificationsSubjectEntity(
                        subjectId = subject.id.toString(),
                        subjectName = subject.name,
                        controlEventName = controlEvent.fullName,
                        currentPoints = controlEvent.currentPoints,
                        maxPoints = controlEvent.maxPoints
                    )
                )
            }
        }
    }

    companion object {
        const val DEBT_POSTFIX = "DEBT"
        const val NO_DEBT_POSTFIX = ""
    }
}
