package com.example.notesapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notesapp.components.NoteCard
import com.example.notesapp.components.SearchBar
import com.example.notesapp.data.Note
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NotesScreen(
    navController: NavController,
    notesList: MutableList<Note>,
    darkMode: Boolean,
    onDarkModeToggle: () -> Unit
) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var titleError by remember {
        mutableStateOf(false)
    }

    var descError by remember {
        mutableStateOf(false)
    }

    var selectedTab by remember {
        mutableStateOf(0)
    }

    var searchQuery by remember {
        mutableStateOf("")
    }

    val tabs = listOf(
        "All Notes",
        "Favorites"
    )

    val context = LocalContext.current

    val filteredNotes = notesList.filter {

        it.title.contains(searchQuery, true) ||
                it.description.contains(searchQuery, true)
    }

    val displayedNotes =
        if (selectedTab == 0) {
            filteredNotes
        } else {
            filteredNotes.filter {
                it.isFavorite
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.SpaceBetween,

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Text(
                text = "Notes App",
                fontSize = 30.sp
            )

            Button(
                onClick = {
                    onDarkModeToggle()
                }
            ) {

                Text(
                    if (darkMode)
                        "Light"
                    else
                        "Dark"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        SearchBar(
            searchQuery = searchQuery,
            onSearchChange = {
                searchQuery = it
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

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

            label = {
                Text("Enter Title")
            },

            isError = titleError,

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

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

            label = {
                Text("Enter Description")
            },

            isError = descError,

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {

                titleError = title.isBlank()
                descError = description.isBlank()

                if (!titleError && !descError) {

                    val currentTime = SimpleDateFormat(
                        "dd MMM yyyy, hh:mm a",
                        Locale.getDefault()
                    ).format(Date())

                    notesList.add(

                        Note(
                            id = System.currentTimeMillis().toInt(),
                            title = title,
                            description = description,
                            createdAt = currentTime
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

            modifier = Modifier.align(
                Alignment.CenterHorizontally
            )
        ) {

            Text("Add Note")
        }

        Spacer(modifier = Modifier.height(20.dp))

        TabRow(
            selectedTabIndex = selectedTab
        ) {

            tabs.forEachIndexed { index, title ->

                Tab(
                    selected = selectedTab == index,

                    onClick = {
                        selectedTab = index
                    },

                    text = {
                        Text(title)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (displayedNotes.isEmpty()) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "No Notes Found",
                    fontSize = 20.sp
                )
            }

        } else {

            LazyColumn {

                items(displayedNotes) { note ->

                    NoteCard(
                        note = note,
                        navController = navController,

                        onDelete = {
                            notesList.remove(note)
                        },

                        onFavoriteToggle = {
                            note.isFavorite =
                                !note.isFavorite
                        }
                    )
                }
            }
        }
    }
}