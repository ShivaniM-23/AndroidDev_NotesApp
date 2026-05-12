package com.example.notesapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(

    primary = Color(0xFF90CAF9),

    secondary = Color(0xFF80CBC4),

    background = Color(0xFF121212),

    surface = Color(0xFF1E1E1E),

    surfaceVariant = Color(0xFF2A2A2A)
)

private val LightColors = lightColorScheme(

    primary = Color(0xFF1565C0),

    secondary = Color(0xFF00897B),

    background = Color(0xFFF5F7FA),

    surface = Color.White,

    surfaceVariant = Color(0xFFE8EEF5)
)

@Composable
fun NotesAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme =
        if (darkTheme)
            DarkColors
        else
            LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}