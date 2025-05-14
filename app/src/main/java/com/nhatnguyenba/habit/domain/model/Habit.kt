package com.nhatnguyenba.habit.domain.model

data class Habit(
    val id: String,
    val name: String,
    val durationMinutes: Int,
    val reminderTime: String,
    val periods: List<String>,
    val isDaily: Boolean
)