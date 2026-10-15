package com.workoutmanager.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.workoutmanager.data.local.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    // 1. Get all exercises for a specific workout
    @Query("SELECT * FROM exercises WHERE workout_id = :workoutId ORDER BY created_at ASC")
    fun getExercisesForWorkout(workoutId: Long): Flow<List<ExerciseEntity>>

    // 2. Insert a new exercise
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: ExerciseEntity): Long

    // 3. Update an exercise (e.g., target reps, notes)
    @Update
    suspend fun updateExercise(exercise: ExerciseEntity)

    // 4. Quick-update just the weight after a gym set!
    @Query("UPDATE exercises SET last_weight = :newWeight WHERE id = :exerciseId")
    suspend fun updateWeight(exerciseId: Long, newWeight: Double)

    // 5. Delete an exercise
    @Delete
    suspend fun deleteExercise(exercise: ExerciseEntity)
}