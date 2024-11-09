package model.subjects.subjectsJson

import model.scheduleJson.Semester
import model.subjects.DisplaySubject
import model.subjects.subjectsJson.jsonElements.SubjectFromWeb

interface SubjectsData {
    val subjects: List<SubjectFromWeb>
    val debts: List<SubjectFromWeb>
    val semesters: List<Semester>

    fun getDisplaySubjects(): List<DisplaySubject> = subjects.map {
        DisplaySubject(
            id = it.id.toString(),
            name = it.name,
            currentPoints = it.currentPoints,
            maxPoints = it.maxPoints.toString()

        )
    }
}