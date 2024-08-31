package com.luntikius.betterorioks

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import database.ScheduleDatabase
import database.getDatabaseBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val databaseBuilder = getDatabaseBuilder(this)
        val database = ScheduleDatabase.getRoomDatabase(databaseBuilder)
        enableEdgeToEdge()
        setContent {
            App(database.getDao())
        }
    }
}
