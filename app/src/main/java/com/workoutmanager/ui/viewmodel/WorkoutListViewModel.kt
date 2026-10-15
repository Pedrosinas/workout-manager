package com.workoutmanager.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workoutmanager.data.local.entity.WorkoutEntity
import com.workoutmanager.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * The data snapshot that our HomeScreen will display.
 */
data class WorkoutListUiState(
    val workouts: List<WorkoutEntity> = emptyList(),
    val isLoading: Boolean = false
)

class WorkoutListViewModel(private val repository: WorkoutRepository) : ViewModel() {

    // Converts our database Flow into a UI StateFlow
    val uiState: StateFlow<WorkoutListUiState> = repository.allWorkouts
        .combine(MutableStateFlow(false)) { workouts, _ ->
            WorkoutListUiState(workouts = workouts, isLoading = false)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Pauses when screen is off to save battery
            initialValue = WorkoutListUiState(isLoading = true)
        )

    fun createWorkout(name: String, description: String = "") {
        if (name.isBlank()) return
        viewModelScope.launch {
            repository.createWorkout(name, description)
        }
    }

    fun deleteWorkout(workout: WorkoutEntity) {
        viewModelScope.launch {
            repository.deleteWorkout(workout)
        }
    }
}