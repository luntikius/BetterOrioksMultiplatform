package com.luntikius.betterorioks.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import model.ScheduleWindow
import ui.scheduleScreen.WindowItem

@Preview
@Composable
fun WindowItemsPreview(){
    Column {
        WindowItem(
            scheduleWindow = ScheduleWindow(windowDuration = 1)
        )
        WindowItem(
            scheduleWindow = ScheduleWindow(windowDuration = 2)
        )
        WindowItem(
            scheduleWindow = ScheduleWindow(windowDuration = 3)
        )
        WindowItem(
            scheduleWindow = ScheduleWindow(windowDuration = 5)
        )
        WindowItem(
            scheduleWindow = ScheduleWindow(windowDuration = 11)
        )
        WindowItem(
            scheduleWindow = ScheduleWindow(windowDuration = 31)
        )
    }
}