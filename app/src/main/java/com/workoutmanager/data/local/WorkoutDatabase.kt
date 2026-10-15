package com.workoutmanager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.workoutmanager.data.local.dao.ExerciseDao
import com.workoutmanager.data.local.dao.WorkoutDao
import com.workoutmanager.data.local.entity.ExerciseEntity
import com.workoutmanager.data.local.entity.WorkoutEntity

@Database(
    entities = [WorkoutEntity::class, ExerciseEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WorkoutDatabase : RoomDatabase() {
    // Expose our two DAOs
    abstract fun workoutDao(): WorkoutDao
    abstract fun exerciseDao(): ExerciseDao

    companion object {
        const val DATABASE_NAME = "workout_manager.db"
    }
}