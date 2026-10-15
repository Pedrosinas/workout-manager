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

data class WorkoutListUiState(
    val workouts: List<WorkoutEntity> = emptyList(),
    val isLoading: Boolean = false
)

class WorkoutListViewModel(
    private val repository: WorkoutRepository
) : ViewModel() {

    val uiState: StateFlow<WorkoutListUiState> = repository.allWorkouts
        .combine(MutableStateFlow(false)) { workouts, _ ->
            WorkoutListUiState(workouts = workouts, isLoading = false)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = WorkoutListUiState(isLoading = true)
        )

    fun createWorkout(name: String, description: String = "") {
        if (name.isBlank()) return
        viewModelScope.launch { repository.createWorkout(name, description) }
    }

    // NOVO: Função para salvar edição de treino
    fun updateWorkout(workout: WorkoutEntity, newName: String, newDescription: String) {
        if (newName.isBlank()) return
        viewModelScope.launch { repository.updateWorkout(workout, newName, newDescription) }
    }

    fun deleteWorkout(workout: WorkoutEntity) {
        viewModelScope.launch { repository.deleteWorkout(workout) }
    }
}