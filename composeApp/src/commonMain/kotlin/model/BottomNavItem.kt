package model

import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.academic_performance
import betterorioks.composeapp.generated.resources.menu
import betterorioks.composeapp.generated.resources.scheldule
import org.jetbrains.compose.resources.DrawableResource

sealed class BottomNavItem(val icon: DrawableResource, val screen: Any, val restoreState: Boolean) {
    data object Schedule : BottomNavItem(Res.drawable.scheldule, ScheduleScreen, true)
    data object Subjects : BottomNavItem(Res.drawable.academic_performance, SubjectsScreen, false)
    data object Menu : BottomNavItem(Res.drawable.menu, MenuScreen, false)
}