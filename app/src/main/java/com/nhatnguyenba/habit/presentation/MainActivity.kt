package com.nhatnguyenba.habit.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.nhatnguyenba.habit.presentation.navigation.HabitTrackerNavGraph
import com.nhatnguyenba.habit.presentation.theme.DailyHabitTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyHabitTrackerTheme {
                val navController = rememberNavController()
                HabitTrackerNavGraph(navController = navController)
            }
        }
    }
}