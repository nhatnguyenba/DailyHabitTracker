package com.nhatnguyenba.habit.data.mapper

import com.nhatnguyenba.habit.data.local.entity.HabitEntity
import com.nhatnguyenba.habit.domain.model.Habit

class HabitMapper {
    fun mapFromEntity(entity: HabitEntity): Habit {
        return Habit(
            id = entity.id,
            name = entity.name,
            durationMinutes = entity.durationMinutes,
            reminderTime = entity.reminderTime,
            periods = entity.periods,
            isDaily = entity.isDaily
        )
    }

    fun mapToEntity(domain: Habit): HabitEntity {
        return HabitEntity(
            id = domain.id,
            name = domain.name,
            durationMinutes = domain.durationMinutes,
            reminderTime = domain.reminderTime,
            periods = domain.periods,
            isDaily = domain.isDaily
        )
    }
}