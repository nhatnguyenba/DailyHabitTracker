package com.nhatnguyenba.habit.domain.usecase

import com.nhatnguyenba.habit.domain.model.Habit
import com.nhatnguyenba.habit.domain.repository.HabitRepository
import javax.inject.Inject

class AddHabitUseCase @Inject constructor(private val repository: HabitRepository) {
    suspend operator fun invoke(habit: Habit) = repository.addHabit(habit)
}