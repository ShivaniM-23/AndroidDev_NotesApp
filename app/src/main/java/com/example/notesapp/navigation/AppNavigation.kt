package com.example.notesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notesapp.screens.DetailScreen
import com.example.notesapp.screens.NotesScreen
import com.example.notesapp.viewmodel.NoteViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun AppNavigation(
    darkTheme: Boolean,
    onThemeChange: () -> Unit
) {

    val viewModel = remember {
        NoteViewModel()
    }

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "notes"
    ) {

        composable("notes") {

            NotesScreen(
                navController = navController,
                viewModel = viewModel,
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
                viewModel = viewModel
            )
        }
    }
}