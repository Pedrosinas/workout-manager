package com.workoutmanager.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workoutmanager.data.local.entity.ExerciseEntity
import com.workoutmanager.data.local.entity.WorkoutEntity
import com.workoutmanager.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class WorkoutDetailUiState(
    val workout: WorkoutEntity? = null,
    val exercises: List<ExerciseEntity> = emptyList()
)

class WorkoutDetailViewModel(
    private val repository: WorkoutRepository,
    private val workoutId: Long
) : ViewModel() {

    val uiState: StateFlow<WorkoutDetailUiState> = combine(
        repository.getWorkout(workoutId),
        repository.getExercisesForWorkout(workoutId)
    ) { workout, exercises ->
        WorkoutDetailUiState(workout = workout, exercises = exercises)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = WorkoutDetailUiState()
    )

    fun addExercise(name: String, targetReps: String, weight: Double, notes: String = "") {
        if (name.isBlank() || targetReps.isBlank() || weight < 0) return
        viewModelScope.launch {
            repository.addExercise(workoutId, name, targetReps, weight, notes)
        }
    }

    // NOVO: Função para salvar edição do exercício
    fun updateExercise(exercise: ExerciseEntity, name: String, targetReps: String, weight: Double, notes: String) {
        if (name.isBlank() || targetReps.isBlank() || weight < 0) return
        viewModelScope.launch {
            repository.updateExercise(exercise, name, targetReps, weight, notes)
        }
    }

    fun stepWeight(exercise: ExerciseEntity, increase: Boolean) {
        val nextWeight = if (increase) exercise.lastWeight + 1.25 else (exercise.lastWeight - 1.25).coerceAtLeast(0.0)
        viewModelScope.launch {
            repository.updateWeight(exercise.id, nextWeight)
        }
    }

    fun deleteExercise(exercise: ExerciseEntity) {
        viewModelScope.launch {
            repository.deleteExercise(exercise)
        }
    }
}