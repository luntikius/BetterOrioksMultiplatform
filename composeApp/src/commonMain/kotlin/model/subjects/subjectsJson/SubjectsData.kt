package model.subjects.subjectsJson

import model.schedule.SemesterDates
import model.schedule.scheduleJson.Semester
import model.subjectPerformance.ControlEventsListItem
import model.subjectPerformance.DisplaySubjectPerformance
import model.subjectPerformance.SubjectPerformanceListItems
import model.subjects.SubjectListItem
import model.subjects.subjectsJson.jsonElements.ControlEvent
import model.subjects.subjectsJson.jsonElements.SubjectFromWeb

interface SubjectsData {
    val subjects: List<SubjectFromWeb>
    val offsetSubjects: List<SubjectFromWeb>
    val debts: List<SubjectFromWeb>
    val semesters: List<Semester>

    val allSubjects: List<SubjectFromWeb>
        get() = subjects + offsetSubjects

    val subjectListItems: List<SubjectListItem>
        get() = allSubjects.map {
            it.toSubjectListItem()
        }

    val displaySubjectPerformance: Map<String, DisplaySubjectPerformance>
        get() = buildMap {
            subjects.forEach { subject ->
                val id = subject.id.toString()
                val weeksPassedSinceSemesterStart = getLastSemesterDates().weeksPassedSinceSemesterStart
                val controlEventsList = buildControlEventsList(
                    subject.controlEvents,
                    weeksPassedSinceSemesterStart,
                    subject.formOfControl.name
                )
                val displaySubjectPerformance = DisplaySubjectPerformance(
                    subject = subject.toSubjectListItem(),
                    subjectPerformanceListItems = SubjectPerformanceListItems(
                        controlEvents = controlEventsList,
                        controlForm = subject.formOfControl.name.ifBlank { null },
                        teachers = subject.teachers
                    )
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
        controlForm: String
    ): List<ControlEventsListItem> = buildSet {
        controlEvents.forEach { controlEvent ->
            val weeksBeforeEvent = maxOf(-1, controlEvent.week - weeksPassedSinceSemesterStart - 1)
            val weeksLeftItem = ControlEventsListItem.WeeksLeftItem(weeksBeforeEvent)
            if (!this.contains(weeksLeftItem)) add(weeksLeftItem)
            add(controlEvent.toControlEventItem(controlForm))
        }
    }.toList()
}
