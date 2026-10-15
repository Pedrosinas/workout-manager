package com.workoutmanager.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Paleta de Cores Escuras (Dark Mode de Academia)
val FundoPreto = Color(0xFF0F172A)      // Fundo principal escuro profundo
val CartaoGrafite = Color(0xFF1E293B)   // Fundo dos cartões de treino e exercício
val BordaSuave = Color(0xFF334155)      // Linhas e bordas
val AzulDestaque = Color(0xFF38BDF8)    // Cor primária dos botões e pesos
val TextoClaro = Color(0xFFF8FAFC)      // Texto principal de alto contraste
val TextoSecundario = Color(0xFF94A3B8) // Texto de notas e legendas
val VermelhoExcluir = Color(0xFFF87171) // Cor de deletar

private val DarkColorScheme = darkColorScheme(
    primary = AzulDestaque,
    onPrimary = Color(0xFF0F172A),
    background = FundoPreto,
    onBackground = TextoClaro,
    surface = CartaoGrafite,
    onSurface = TextoClaro,
    surfaceVariant = CartaoGrafite,
    onSurfaceVariant = TextoSecundario,
    error = VermelhoExcluir
)

@Composable
fun WorkoutManagerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}