package com.nhatnguyenba.habit.domain.repository

import com.nhatnguyenba.habit.domain.model.Habit
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun getHabits(): Flow<List<Habit>>
    suspend fun addHabit(habit: Habit)
    suspend fun deleteHabit(habitId: String)
}