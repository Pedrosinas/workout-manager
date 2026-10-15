package com.workoutmanager.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.workoutmanager.data.local.entity.WorkoutEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    // 1. Get all workouts, newest first (Live observable Flow!)
    @Query("SELECT * FROM workouts ORDER BY created_at DESC")
    fun getAllWorkouts(): Flow<List<WorkoutEntity>>

    // 2. Get a single workout by its ID
    @Query("SELECT * FROM workouts WHERE id = :workoutId LIMIT 1")
    fun getWorkoutById(workoutId: Long): Flow<WorkoutEntity?>

    // 3. Insert a new workout (REPLACE means if it already exists, update it)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: WorkoutEntity): Long

    // 4. Update an existing workout (e.g. changing its name)
    @Update
    suspend fun updateWorkout(workout: WorkoutEntity)

    // 5. Delete a workout (SQLite CASCADE will auto-delete its exercises too!)
    @Delete
    suspend fun deleteWorkout(workout: WorkoutEntity)
}