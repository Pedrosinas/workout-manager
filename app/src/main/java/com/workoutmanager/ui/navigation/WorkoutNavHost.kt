package com.workoutmanager.ui.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.workoutmanager.data.repository.WorkoutRepository
import com.workoutmanager.ui.screens.WorkoutDetailScreen
import com.workoutmanager.ui.screens.WorkoutListScreen
import com.workoutmanager.ui.viewmodel.WorkoutDetailViewModel
import com.workoutmanager.ui.viewmodel.WorkoutListViewModel

@Composable
fun WorkoutNavHost(repository: WorkoutRepository) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "workout_list"
    ) {
        composable("workout_list") {
            val viewModel = remember { WorkoutListViewModel(repository) }
            val uiState by viewModel.uiState.collectAsState()

            WorkoutListScreen(
                uiState = uiState,
                onCreateWorkout = { name, desc -> viewModel.createWorkout(name, desc) },
                onUpdateWorkout = { workout, name, desc -> viewModel.updateWorkout(workout, name, desc) },
                onDeleteWorkout = { workout -> viewModel.deleteWorkout(workout) },
                onWorkoutClicked = { workoutId ->
                    navController.navigate("workout_detail/$workoutId")
                }
            )
        }

        composable(
            route = "workout_detail/{workoutId}",
            arguments = listOf(navArgument("workoutId") { type = NavType.LongType })
        ) { backStackEntry ->
            val workoutId = backStackEntry.arguments?.getLong("workoutId") ?: 0L
            val viewModel = remember(workoutId) { WorkoutDetailViewModel(repository, workoutId) }
            val uiState by viewModel.uiState.collectAsState()

            WorkoutDetailScreen(
                uiState = uiState,
                onBack = { navController.popBackStack() },
                onAddExercise = { name, reps, weight, notes ->
                    viewModel.addExercise(name, reps, weight, notes)
                },
                onUpdateExercise = { exercise, name, reps, weight, notes ->
                    viewModel.updateExercise(exercise, name, reps, weight, notes)
                },
                onStepWeight = { exercise, increase ->
                    viewModel.stepWeight(exercise, increase)
                },
                onDeleteExercise = { exercise ->
                    viewModel.deleteExercise(exercise)
                }
            )
        }
    }
}