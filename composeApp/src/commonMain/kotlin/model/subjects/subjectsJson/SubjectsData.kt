package model.subjects.subjectsJson

import model.schedule.scheduleJson.Semester
import model.subjects.DisplaySubject
import model.subjects.subjectsJson.jsonElements.SubjectFromWeb

interface SubjectsData {
    val subjects: List<SubjectFromWeb>
    val offsetSubjects: List<SubjectFromWeb>
    val debts: List<SubjectFromWeb>
    val semesters: List<Semester>

    val displaySubjects: List<DisplaySubject>
        get() = (subjects + offsetSubjects).map {
            DisplaySubject(
                id = it.id.toString(),
                name = it.name,
                currentPoints = it.currentPoints,
                maxPoints = it.maxPoints.toString()

            )
        }
}
