package model.subjectPerformance

import model.subjects.SubjectListItem

data class DisplaySubjectPerformance(
    val subject: SubjectListItem,
    val subjectPerformanceListItems: SubjectPerformanceListItems
)
