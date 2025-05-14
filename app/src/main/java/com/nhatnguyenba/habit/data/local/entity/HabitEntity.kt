package com.nhatnguyenba.habit.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey val id: String,
    val name: String,
    val durationMinutes: Int,
    val reminderTime: String,
    val periods: List<String>,
    val isDaily: Boolean
)