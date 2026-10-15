package com.workoutmanager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.workoutmanager.data.local.entity.ExerciseEntity
import com.workoutmanager.ui.components.AddExerciseDialog
import com.workoutmanager.ui.components.EditExerciseDialog
import com.workoutmanager.ui.viewmodel.WorkoutDetailUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDetailScreen(
    uiState: WorkoutDetailUiState,
    onBack: () -> Unit,
    onAddExercise: (name: String, reps: String, weight: Double, notes: String) -> Unit,
    onUpdateExercise: (exercise: ExerciseEntity, name: String, reps: String, weight: Double, notes: String) -> Unit,
    onStepWeight: (exercise: ExerciseEntity, increase: Boolean) -> Unit,
    onDeleteExercise: (ExerciseEntity) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var exerciseToEdit by remember { mutableStateOf<ExerciseEntity?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.workout?.name ?: "Treino", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Exercício")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (uiState.exercises.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Nenhum Exercício Adicionado",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Adicione exercícios com repetições alvo (ex: 8-12) e a carga da última sessão em kg.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { showAddDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Adicionar Primeiro Exercício")
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    // AQUI: bottom = 90.dp garante que o botão + NUNCA fique sobre o botão de + peso do último exercício!
                    contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 90.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(items = uiState.exercises, key = { it.id }) { exercise ->
                        OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = exercise.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Row {
                                        IconButton(onClick = { exerciseToEdit = exercise }) {
                                            Icon(Icons.Default.Edit, contentDescription = "Editar")
                                        }
                                        IconButton(onClick = { onDeleteExercise(exercise) }) {
                                            Icon(
                                                Icons.Default.Delete,
                                                contentDescription = "Excluir",
                                                tint = MaterialTheme.colorScheme.error
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            text = "Repetições Alvo",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            text = exercise.targetRepetitions,
                                            style = MaterialTheme.typography.bodyLarge,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }

                                    // Botões de Micro-Carga (+ / - 1.25 kg)
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        FilledTonalIconButton(
                                            onClick = { onStepWeight(exercise, false) },
                                            modifier = Modifier.size(36.dp)
                                        ) {
                                            Text("-", fontWeight = FontWeight.Bold)
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "${exercise.lastWeight} kg",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        FilledTonalIconButton(
                                            onClick = { onStepWeight(exercise, true) },
                                            modifier = Modifier.size(36.dp)
                                        ) {
                                            Text("+", fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }

                                if (exercise.notes.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "Obs: ${exercise.notes}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddExerciseDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name, reps, weight, notes ->
                onAddExercise(name, reps, weight, notes)
                showAddDialog = false
            }
        )
    }

    exerciseToEdit?.let { exercise ->
        EditExerciseDialog(
            exercise = exercise,
            onDismiss = { exerciseToEdit = null },
            onConfirm = { name, reps, weight, notes ->
                onUpdateExercise(exercise, name, reps, weight, notes)
                exerciseToEdit = null
            }
        )
    }
}