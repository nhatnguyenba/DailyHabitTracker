package com.nhatnguyenba.habit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nhatnguyenba.habit.domain.model.Habit
import com.nhatnguyenba.habit.domain.usecase.AddHabitUseCase
import com.nhatnguyenba.habit.domain.usecase.DeleteHabitUseCase
import com.nhatnguyenba.habit.domain.usecase.GetHabitsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(
    private val addHabitUseCase: AddHabitUseCase,
    private val getHabitsUseCase: GetHabitsUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase
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
}