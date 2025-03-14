package model

import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.academic_performance
import betterorioks.composeapp.generated.resources.menu
import betterorioks.composeapp.generated.resources.scheldule
import org.jetbrains.compose.resources.DrawableResource

sealed class BottomNavItem(val icon: DrawableResource, val screen: BetterOrioksScreen, val restoreState: Boolean) {
    data object Schedule : BottomNavItem(Res.drawable.scheldule, BetterOrioksScreen.ScheduleScreen, true)
    data object Subjects : BottomNavItem(Res.drawable.academic_performance, BetterOrioksScreen.SubjectsScreen, false)
    data object Menu : BottomNavItem(Res.drawable.menu, BetterOrioksScreen.MenuScreen, false)
}
