package model.subjectPerformance

import model.subjects.SubjectListItem
import model.subjects.subjectsJson.jsonElements.Teacher

data class DisplaySubjectPerformance(
    val subject: SubjectListItem,
    val controlEvents: List<ControlEventsListItem>,
    val controlForm: String?,
    val teachers: List<Teacher>,
)
