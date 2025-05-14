package com.nhatnguyenba.habit.presentation.screen

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nhatnguyenba.habit.domain.model.Habit
import com.nhatnguyenba.habit.presentation.viewmodel.HabitViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHabitScreen(
    navController: NavController,
    viewModel: HabitViewModel = hiltViewModel()
) {
    var habitName by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var isDaily by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf(LocalTime.of(21, 30)) }
    var selectedPeriods by remember { mutableStateOf(listOf<String>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Habit") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Habit Name
            OutlinedTextField(
                value = habitName,
                onValueChange = { habitName = it },
                label = { Text("Habit Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Daily Checkbox
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isDaily,
                    onCheckedChange = { isDaily = it }
                )
                Text("Repeat everyday")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Duration
            OutlinedTextField(
                value = duration,
                onValueChange = { duration = it },
                label = { Text("Duration (minutes)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Time Picker
            TimePickerSection(
                selectedTime = selectedTime,
                onTimeSelected = { selectedTime = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Period Selection
            PeriodSelection(
                selectedPeriods = selectedPeriods,
                onPeriodSelected = { selectedPeriods = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Save Button
            Button(
                onClick = {
                    val newHabit = Habit(
                        id = UUID.randomUUID().toString(),
                        name = habitName,
                        durationMinutes = duration.toIntOrNull() ?: 0,
                        reminderTime = selectedTime.format(DateTimeFormatter.ofPattern("hh:mm a", Locale.US)),
                        periods = selectedPeriods,
                        isDaily = isDaily
                    )
                    viewModel.addHabit(newHabit)
                    viewModel.scheduleReminder(newHabit)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = habitName.isNotBlank() && duration.isNotBlank()
            ) {
                Text("Save Habit")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePickerSection(
    selectedTime: LocalTime,
    onTimeSelected: (LocalTime) -> Unit
) {
    var showTimePicker by remember { mutableStateOf(false) }

    Column {
        Text("Reminder Time", style = MaterialTheme.typography.titleMedium)
        Button(
            onClick = { showTimePicker = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(selectedTime.format(DateTimeFormatter.ofPattern("hh:mm a")))
        }

        if (showTimePicker) {
            TimePickerDialog(
                onDismiss = { showTimePicker = false },
                onConfirm = { timePickerState ->
                    onTimeSelected(LocalTime.of(timePickerState.hour, timePickerState.minute))
                    showTimePicker = false
                },
                hourOfDay = selectedTime.hour,
                minute = selectedTime.minute,
                is24HourView = true
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: (TimePickerState) -> Unit,
    hourOfDay: Int,
    minute: Int,
    is24HourView: Boolean
) {
    val timePickerState = rememberTimePickerState(
        initialHour = hourOfDay,
        initialMinute = minute,
        is24Hour = is24HourView,
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(timePickerState) }) {
                Text("OK")
            }
        },
        text = {
            TimePicker(
                state = timePickerState,
            )
        }
    )
}

@Composable
private fun PeriodSelection(
    selectedPeriods: List<String>,
    onPeriodSelected: (List<String>) -> Unit
) {
    val periods = listOf("Morning", "Afternoon", "Evening")

    Column {
        Text("Show on", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            periods.forEach { period ->
                val isSelected = period in selectedPeriods // Kiểm tra trong danh sách
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        val newList = if (isSelected) {
                            selectedPeriods - period // Xóa nếu đã chọn
                        } else {
                            selectedPeriods + period // Thêm nếu chưa chọn
                        }
                        onPeriodSelected(newList)
                    },
                    label = { Text(period) }
                )
            }
        }
    }
}