package com.example.notesapp

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

data class Note(
    val id: Int,
    val title: String,
    val description: String
)

@Composable
fun AppNavigation() {

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
                notesList = notesList
            )
        }

        composable(
            route = "detail/{id}/{title}/{description}"
        ) { backStackEntry ->

            val id =
                backStackEntry.arguments?.getString("id")?.toInt() ?: 0

            val encodedTitle =
                backStackEntry.arguments?.getString("title") ?: ""

            val encodedDescription =
                backStackEntry.arguments?.getString("description") ?: ""

            val title = URLDecoder.decode(encodedTitle, "UTF-8")
            val description = URLDecoder.decode(encodedDescription, "UTF-8")

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

@Composable
fun NotesScreen(
    navController: NavController,
    notesList: MutableList<Note>
) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var titleError by remember { mutableStateOf(false) }
    var descError by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Notes App",
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Title Error
        if (titleError) {
            Text(
                text = "Title is empty",
                color = MaterialTheme.colorScheme.error
            )
        }

        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
                titleError = false
            },
            label = { Text("Enter Title") },
            isError = titleError,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Description Error
        if (descError) {
            Text(
                text = "Description is empty",
                color = MaterialTheme.colorScheme.error
            )
        }

        OutlinedTextField(
            value = description,
            onValueChange = {
                description = it
                descError = false
            },
            label = { Text("Enter Description") },
            isError = descError,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {

                titleError = title.isBlank()
                descError = description.isBlank()

                if (!titleError && !descError) {

                    notesList.add(
                        Note(
                            id = notesList.size + 1,
                            title = title,
                            description = description
                        )
                    )

                    Toast.makeText(
                        context,
                        "Note Added",
                        Toast.LENGTH_SHORT
                    ).show()

                    title = ""
                    description = ""
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Add Note")
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {

            items(notesList) { note ->

                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    Column(
                        modifier = Modifier
                            .padding(12.dp)
                            .clickable {

                                val encodedTitle = URLEncoder.encode(
                                    note.title,
                                    StandardCharsets.UTF_8.toString()
                                )

                                val encodedDescription = URLEncoder.encode(
                                    note.description,
                                    StandardCharsets.UTF_8.toString()
                                )

                                navController.navigate(
                                    "detail/${note.id}/$encodedTitle/$encodedDescription"
                                )
                            }
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = note.title,
                                fontSize = 20.sp
                            )

                            IconButton(
                                onClick = {

                                    notesList.remove(note)

                                    Toast.makeText(
                                        context,
                                        "Note Deleted",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            ) {

                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete"
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = note.description,
                            fontSize = 14.sp,
                            maxLines = 2
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DetailScreen(
    id: Int,
    title: String,
    description: String,
    navController: NavController,
    notesList: MutableList<Note>
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Note Details",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = title,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = description,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Text("Back")
            }

            Button(
                onClick = {

                    val noteToDelete =
                        notesList.find { it.id == id }

                    if (noteToDelete != null) {

                        notesList.remove(noteToDelete)

                        Toast.makeText(
                            context,
                            "Note Deleted",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    navController.popBackStack()
                }
            ) {
                Text("Delete Note")
            }
        }
    }
}