package model

import betterorioks.composeapp.generated.resources.Res
import betterorioks.composeapp.generated.resources.menu
import betterorioks.composeapp.generated.resources.schedule
import betterorioks.composeapp.generated.resources.subjects
import org.jetbrains.compose.resources.DrawableResource

sealed class BottomNavItem(val icon: DrawableResource, val screen: BetterOrioksScreen, val restoreState: Boolean) {
    data object Schedule : BottomNavItem(Res.drawable.schedule, BetterOrioksScreen.ScheduleScreen, true)
    data object Subjects : BottomNavItem(Res.drawable.subjects, BetterOrioksScreen.SubjectsScreen, false)
    data object Menu : BottomNavItem(Res.drawable.menu, BetterOrioksScreen.MenuScreen, false)
}
