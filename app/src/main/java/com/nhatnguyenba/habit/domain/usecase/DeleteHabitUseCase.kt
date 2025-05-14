package com.nhatnguyenba.habit.domain.usecase

import com.nhatnguyenba.habit.domain.repository.HabitRepository
import javax.inject.Inject

class DeleteHabitUseCase @Inject constructor(private val repository: HabitRepository) {
    suspend operator fun invoke(id: String) = repository.deleteHabit(id)
}