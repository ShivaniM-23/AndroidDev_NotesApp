package com.example.notesapp.navigation

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
fun AppNavigation(
    darkTheme: Boolean,
    onThemeChange: () -> Unit
) {

    val notesList = remember {
        mutableStateListOf<Note>()
    }

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "notes"
    ) {

        composable("notes") {

            NotesScreen(
                navController = navController,
                notesList = notesList,
                darkMode = darkTheme,
                onDarkModeToggle = onThemeChange
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