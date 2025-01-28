package model.subjects

import betterorioks.composeapp.generated.resources.Credit
import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.bad_mark
import betterorioks.composeapp.generated.resources.excellent_mark
import betterorioks.composeapp.generated.resources.good_mark
import betterorioks.composeapp.generated.resources.ok_mark
import org.jetbrains.compose.resources.StringResource

enum class SubjectsGroup(
    val nameRes: StringResource,
    val filterLambda: (SubjectListItem) -> Boolean
) {
    Unfinished(
        nameRes = Res.string.bad_mark,
        filterLambda = {
            it.currentPoints.toDoubleOrNull() == null ||
                (it.currentPoints.toDoubleOrNull() !== null && it.currentPoints.toDouble() < 50)
        }
    ),
    Normal(
        nameRes = Res.string.ok_mark,
        filterLambda = {
            !it.subjectInfo.formOfControl.isCredit &&
                it.currentPoints.toDoubleOrNull() != null &&
                it.currentPoints.toDouble() >= 50 &&
                it.currentPoints.toDouble() < 70
        }
    ),
    Good(
        nameRes = Res.string.good_mark,
        filterLambda = {
            !it.subjectInfo.formOfControl.isCredit &&
                it.currentPoints.toDoubleOrNull() != null &&
                it.currentPoints.toDouble() >= 70 &&
                it.currentPoints.toDouble() < 86
        }
    ),
    Excellent(
        nameRes = Res.string.excellent_mark,
        filterLambda = {
            !it.subjectInfo.formOfControl.isCredit &&
                it.currentPoints.toDoubleOrNull() != null &&
                it.currentPoints.toDouble() >= 86
        }
    ),
    Credit(
        nameRes = Res.string.Credit,
        filterLambda = {
            it.subjectInfo.formOfControl.isCredit &&
                it.currentPoints.toDoubleOrNull() != null &&
                it.currentPoints.toDouble() >= 50
        }
    )
}
