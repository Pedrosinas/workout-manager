package com.workoutmanager.data.repository

import com.workoutmanager.data.local.dao.ExerciseDao
import com.workoutmanager.data.local.dao.WorkoutDao
import com.workoutmanager.data.local.entity.ExerciseEntity
import com.workoutmanager.data.local.entity.WorkoutEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class WorkoutRepository(
    private val workoutDao: WorkoutDao,
    private val exerciseDao: ExerciseDao
) {
    val allWorkouts: Flow<List<WorkoutEntity>> = workoutDao.getAllWorkouts()

    fun getWorkout(workoutId: Long): Flow<WorkoutEntity?> = workoutDao.getWorkoutById(workoutId)

    suspend fun createWorkout(name: String, description: String = ""): Long =
        withContext(Dispatchers.IO) {
            workoutDao.insertWorkout(WorkoutEntity(name = name.trim(), description = description.trim()))
        }

    // NOVO: Atualizar treino
    suspend fun updateWorkout(workout: WorkoutEntity, newName: String, newDescription: String) =
        withContext(Dispatchers.IO) {
            workoutDao.updateWorkout(workout.copy(name = newName.trim(), description = newDescription.trim()))
        }

    suspend fun deleteWorkout(workout: WorkoutEntity) =
        withContext(Dispatchers.IO) {
            workoutDao.deleteWorkout(workout)
        }

    fun getExercisesForWorkout(workoutId: Long): Flow<List<ExerciseEntity>> =
        exerciseDao.getExercisesForWorkout(workoutId)

    suspend fun addExercise(workoutId: Long, name: String, targetReps: String, weight: Double, notes: String = ""): Long =
        withContext(Dispatchers.IO) {
            exerciseDao.insertExercise(
                ExerciseEntity(
                    workoutId = workoutId,
                    name = name.trim(),
                    targetRepetitions = targetReps.trim(),
                    lastWeight = weight,
                    notes = notes.trim()
                )
            )
        }

    // NOVO: Atualizar exercício completo
    suspend fun updateExercise(exercise: ExerciseEntity, name: String, reps: String, weight: Double, notes: String) =
        withContext(Dispatchers.IO) {
            exerciseDao.updateExercise(
                exercise.copy(
                    name = name.trim(),
                    targetRepetitions = reps.trim(),
                    lastWeight = weight,
                    notes = notes.trim()
                )
            )
        }

    suspend fun updateWeight(exerciseId: Long, newWeight: Double) =
        withContext(Dispatchers.IO) {
            exerciseDao.updateWeight(exerciseId, newWeight)
        }

    suspend fun deleteExercise(exercise: ExerciseEntity) =
        withContext(Dispatchers.IO) {
            exerciseDao.deleteExercise(exercise)
        }
}