package com.nhatnguyenba.habit.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nhatnguyenba.habit.presentation.screen.AddHabitScreen
import com.nhatnguyenba.habit.presentation.screen.HabitListScreen

@Composable
fun HabitTrackerNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "habit_list"
    ) {
        composable("habit_list") {
            HabitListScreen(
                navController = navController,
            )
        }
        composable("add_habit") {
            AddHabitScreen(
                navController = navController,
            )
        }
    }
}