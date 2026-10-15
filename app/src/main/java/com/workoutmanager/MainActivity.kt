package com.workoutmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.workoutmanager.data.local.WorkoutDatabase
import com.workoutmanager.data.repository.WorkoutRepository
import com.workoutmanager.ui.navigation.WorkoutNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Initialize local SQLite Room Database
        val database = Room.databaseBuilder(
            applicationContext,
            WorkoutDatabase::class.java,
            WorkoutDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()

        // 2. Initialize the Repository
        val repository = WorkoutRepository(
            workoutDao = database.workoutDao(),
            exerciseDao = database.exerciseDao()
        )

        // 3. Launch our Compose UI
        setContent {
            WorkoutNavHost(repository = repository)
        }
    }
}