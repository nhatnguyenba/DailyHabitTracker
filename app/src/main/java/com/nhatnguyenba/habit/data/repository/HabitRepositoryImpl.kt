package com.nhatnguyenba.habit.data.repository

import com.nhatnguyenba.habit.data.local.dao.HabitDao
import com.nhatnguyenba.habit.data.mapper.HabitMapper
import com.nhatnguyenba.habit.domain.model.Habit
import com.nhatnguyenba.habit.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HabitRepositoryImpl(
    private val dao: HabitDao,
    private val mapper: HabitMapper // Chuyển đổi giữa Habit (Domain) và HabitEntity (Data)
) : HabitRepository {
    override suspend fun getHabits(): Flow<List<Habit>> {
        return dao.getAll().map { list -> list.map { mapper.mapFromEntity(it) } }
    }

    override suspend fun addHabit(habit: Habit) {
        dao.insert(mapper.mapToEntity(habit))
    }

    override suspend fun deleteHabit(habitId: String) {
        dao.deleteHabit(habitId)
    }
}