package com.workoutmanager.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.workoutmanager.data.local.entity.ExerciseEntity
import com.workoutmanager.data.local.entity.WorkoutEntity

@Composable
fun CreateWorkoutDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, description: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Novo Treino") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome do Treino *") },
                    placeholder = { Text("Ex: Treino A - Peito") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Foco / Descrição (Opcional)") },
                    placeholder = { Text("Ex: Foco em peito e tríceps") },
                    maxLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { if (name.isNotBlank()) onConfirm(name.trim(), description.trim()) },
                enabled = name.isNotBlank()
            ) {
                Text("Criar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}

@Composable
fun EditWorkoutDialog(
    workout: WorkoutEntity,
    onDismiss: () -> Unit,
    onConfirm: (name: String, description: String) -> Unit
) {
    var name by remember { mutableStateOf(workout.name) }
    var description by remember { mutableStateOf(workout.description) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Treino") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome do Treino *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Foco / Descrição") },
                    maxLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { if (name.isNotBlank()) onConfirm(name.trim(), description.trim()) },
                enabled = name.isNotBlank()
            ) {
                Text("Salvar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}

@Composable
fun AddExerciseDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, targetReps: String, weight: Double, notes: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var targetReps by remember { mutableStateOf("8-12") }
    var weightText by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    val parsedWeight = weightText.toDoubleOrNull() ?: 0.0
    val isValid = name.isNotBlank() && targetReps.isNotBlank() && parsedWeight >= 0.0

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Adicionar Exercício") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome do Exercício *") },
                    placeholder = { Text("Ex: Supino Reto") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = targetReps,
                    onValueChange = { targetReps = it },
                    label = { Text("Repetições Alvo *") },
                    placeholder = { Text("Ex: 8-12 ou 4x10") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = weightText,
                    onValueChange = { weightText = it },
                    label = { Text("Carga Atual (kg) *") },
                    placeholder = { Text("Ex: 80.0") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Observação / Carga de Halter (Opcional)") },
                    placeholder = { Text("Ex: Pausa de 1s embaixo") },
                    maxLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { if (isValid) onConfirm(name.trim(), targetReps.trim(), parsedWeight, notes.trim()) },
                enabled = isValid
            ) {
                Text("Adicionar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}

@Composable
fun EditExerciseDialog(
    exercise: ExerciseEntity,
    onDismiss: () -> Unit,
    onConfirm: (name: String, targetReps: String, weight: Double, notes: String) -> Unit
) {
    var name by remember { mutableStateOf(exercise.name) }
    var targetReps by remember { mutableStateOf(exercise.targetRepetitions) }
    var weightText by remember { mutableStateOf(exercise.lastWeight.toString()) }
    var notes by remember { mutableStateOf(exercise.notes) }

    val parsedWeight = weightText.toDoubleOrNull() ?: 0.0
    val isValid = name.isNotBlank() && targetReps.isNotBlank() && parsedWeight >= 0.0

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Exercício") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome do Exercício *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = targetReps,
                    onValueChange = { targetReps = it },
                    label = { Text("Repetições Alvo *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = weightText,
                    onValueChange = { weightText = it },
                    label = { Text("Carga (kg) *") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Observação") },
                    maxLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { if (isValid) onConfirm(name.trim(), targetReps.trim(), parsedWeight, notes.trim()) },
                enabled = isValid
            ) {
                Text("Salvar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}