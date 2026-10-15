package com.workoutmanager.data.repository

import com.workoutmanager.data.local.dao.ExerciseDao
import com.workoutmanager.data.local.dao.WorkoutDao
import com.workoutmanager.data.local.entity.ExerciseEntity
import com.workoutmanager.data.local.entity.WorkoutEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Single source of truth coordinating database operations.
 */
class WorkoutRepository(
    private val workoutDao: WorkoutDao,
    private val exerciseDao: ExerciseDao
) {
    // WORKOUT OPERATIONS

    val allWorkouts: Flow<List<WorkoutEntity>> = workoutDao.getAllWorkouts()

    fun getWorkout(workoutId: Long): Flow<WorkoutEntity?> {
        return workoutDao.getWorkoutById(workoutId)
    }

    suspend fun createWorkout(name: String, description: String = ""): Long {
        return withContext(Dispatchers.IO) {
            val workout = WorkoutEntity(
                name = name.trim(),
                description = description.trim()
            )
            workoutDao.insertWorkout(workout)
        }
    }

    suspend fun deleteWorkout(workout: WorkoutEntity) {
        withContext(Dispatchers.IO) {
            workoutDao.deleteWorkout(workout)
        }
    }

    // EXERCISE OPERATIONS

    fun getExercisesForWorkout(workoutId: Long): Flow<List<ExerciseEntity>> {
        return exerciseDao.getExercisesForWorkout(workoutId)
    }

    suspend fun addExercise(
        workoutId: Long,
        name: String,
        targetReps: String,
        weight: Double,
        notes: String = ""
    ): Long {
        return withContext(Dispatchers.IO) {
            val exercise = ExerciseEntity(
                workoutId = workoutId,
                name = name.trim(),
                targetRepetitions = targetReps.trim(),
                lastWeight = weight,
                notes = notes.trim()
            )
            exerciseDao.insertExercise(exercise)
        }
    }

    suspend fun updateWeight(exerciseId: Long, newWeight: Double) {
        withContext(Dispatchers.IO) {
            exerciseDao.updateWeight(exerciseId, newWeight)
        }
    }

    suspend fun deleteExercise(exercise: ExerciseEntity) {
        withContext(Dispatchers.IO) {
            exerciseDao.deleteExercise(exercise)
        }
    }
}