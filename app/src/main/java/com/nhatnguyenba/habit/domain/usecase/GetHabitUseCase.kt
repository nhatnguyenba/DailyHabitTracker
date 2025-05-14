package com.nhatnguyenba.habit.domain.usecase

import com.nhatnguyenba.habit.domain.model.Habit
import com.nhatnguyenba.habit.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHabitsUseCase @Inject constructor(private val repository: HabitRepository) {
    suspend operator fun invoke(): Flow<List<Habit>> = repository.getHabits()
}