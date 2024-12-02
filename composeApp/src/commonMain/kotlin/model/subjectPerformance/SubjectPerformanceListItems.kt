package model.subjectPerformance

import model.subjects.subjectsJson.jsonElements.Teacher

data class SubjectPerformanceListItems(
    val controlEvents: List<ControlEventsListItem>,
    val controlForm: String?,
    val teachers: List<Teacher>
)
