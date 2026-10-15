package com.workoutmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.workoutmanager.data.local.WorkoutDatabase
import com.workoutmanager.data.repository.WorkoutRepository
import com.workoutmanager.ui.navigation.WorkoutNavHost
import com.workoutmanager.ui.theme.WorkoutManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(
            applicationContext,
            WorkoutDatabase::class.java,
            WorkoutDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()

        val repository = WorkoutRepository(
            workoutDao = database.workoutDao(),
            exerciseDao = database.exerciseDao()
        )

        setContent {
            // AQUI: Aplica o tema escuro moderno em todo o aplicativo!
            WorkoutManagerTheme {
                WorkoutNavHost(repository = repository)
            }
        }
    }
}