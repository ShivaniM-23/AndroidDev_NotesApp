package com.example.notesapp.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateListOf
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notesapp.data.Note
import com.example.notesapp.screens.DetailScreen
import com.example.notesapp.screens.NotesScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun AppNavigation() {

    val notesList = remember {
        mutableStateListOf<Note>()
    }

    var darkMode by remember {
        mutableStateOf(false)
    }

    val navController = rememberNavController()

    MaterialTheme(
        colorScheme =
            if (darkMode)
                darkColorScheme()
            else
                lightColorScheme()
    ) {

        NavHost(
            navController = navController,
            startDestination = "notes"
        ) {

            composable("notes") {

                NotesScreen(
                    navController = navController,
                    notesList = notesList,
                    darkMode = darkMode,
                    onDarkModeToggle = {
                        darkMode = !darkMode
                    }
                )
            }

            composable(
                route = "detail/{id}/{title}/{description}"
            ) { backStackEntry ->

                val id =
                    backStackEntry.arguments
                        ?.getString("id")
                        ?.toInt() ?: 0

                val encodedTitle =
                    backStackEntry.arguments
                        ?.getString("title") ?: ""

                val encodedDescription =
                    backStackEntry.arguments
                        ?.getString("description") ?: ""

                val title = URLDecoder.decode(
                    encodedTitle,
                    StandardCharsets.UTF_8.toString()
                )

                val description = URLDecoder.decode(
                    encodedDescription,
                    StandardCharsets.UTF_8.toString()
                )

                DetailScreen(
                    id = id,
                    title = title,
                    description = description,
                    navController = navController,
                    notesList = notesList
                )
            }
        }
    }
}