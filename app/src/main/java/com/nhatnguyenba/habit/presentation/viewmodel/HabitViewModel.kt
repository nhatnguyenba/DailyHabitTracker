package com.nhatnguyenba.habit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.nhatnguyenba.habit.domain.model.Habit
import com.nhatnguyenba.habit.domain.usecase.AddHabitUseCase
import com.nhatnguyenba.habit.domain.usecase.DeleteHabitUseCase
import com.nhatnguyenba.habit.domain.usecase.GetHabitsUseCase
import com.nhatnguyenba.habit.worker.ReminderWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(
    private val addHabitUseCase: AddHabitUseCase,
    private val getHabitsUseCase: GetHabitsUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
    private val workManager: WorkManager
) : ViewModel() {
    private var _habits: Flow<List<Habit>> = emptyFlow()
    val habits: Flow<List<Habit>>
        get() = _habits

    init {
        viewModelScope.launch {
            _habits = getHabitsUseCase()
        }
    }

    fun addHabit(habit: Habit) {
        viewModelScope.launch {
            addHabitUseCase(habit)
        }
    }

    fun deleteHabit(habitId: String) {
        viewModelScope.launch {
            deleteHabitUseCase(habitId)
        }
    }

    fun scheduleReminder(habit: Habit) {
        val reminderTime = parseTime(habit.reminderTime)
        val currentTime = LocalDateTime.now()

        val initialDelay = calculateDelay(currentTime, reminderTime)

        val data = Data.Builder()
            .putString("habit_name", habit.name)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(
            24, TimeUnit.HOURS // Lặp lại hàng ngày
        )
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "reminder_${habit.id}",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

    private fun parseTime(timeString: String): LocalTime {
        val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.US)
        return LocalTime.parse(timeString, formatter)
    }

    private fun calculateDelay(currentTime: LocalDateTime, reminderTime: LocalTime): Long {
        var scheduledTime = currentTime.with(reminderTime)
        if (scheduledTime.isBefore(currentTime)) {
            scheduledTime = scheduledTime.plusDays(1)
        }
        return Duration.between(currentTime, scheduledTime).toMillis()
    }
}