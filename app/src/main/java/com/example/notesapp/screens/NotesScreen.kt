package com.example.notesapp.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notesapp.components.NoteCard
import com.example.notesapp.components.SearchBar
import com.example.notesapp.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NoteViewModel,
    darkMode: Boolean,
    onDarkModeToggle: () -> Unit
) {

    val notesList = viewModel.notes

    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    var titleError by remember {
        mutableStateOf(false)
    }

    var descriptionError by remember {
        mutableStateOf(false)
    }

    var searchQuery by remember {
        mutableStateOf("")
    }

    var selectedTab by remember {
        mutableStateOf(0)
    }

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
                it.isFavorite.value
            }
        }

    Scaffold(

        containerColor =
            MaterialTheme.colorScheme.background,

        topBar = {

            TopAppBar(

                title = {

                    Column {

                        Text(
                            text = "My Notes",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Capture your ideas",
                            fontSize = 13.sp,
                            color =
                                MaterialTheme
                                    .colorScheme
                                    .primary
                        )
                    }
                },

                actions = {

                    IconButton(
                        onClick = {
                            onDarkModeToggle()
                        }
                    ) {

                        Icon(
                            imageVector =
                                if (darkMode)
                                    Icons.Default.LightMode
                                else
                                    Icons.Default.DarkMode,

                            contentDescription = "Theme"
                        )
                    }
                }
            )
        },

        floatingActionButton = {

            FloatingActionButton(
                onClick = {

                    titleError =
                        title.isBlank()

                    descriptionError =
                        description.isBlank()

                    if (!titleError &&
                        !descriptionError
                    ) {

                        viewModel.addNote(
                            title,
                            description
                        )

                        title = ""
                        description = ""

                        Toast.makeText(
                            context,
                            "Note Added",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            ) {

                Icon(
                    imageVector =
                        Icons.Default.NoteAdd,

                    contentDescription = "Add"
                )
            }
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme
                        .colorScheme
                        .background
                )
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(12.dp))

            SearchBar(
                searchQuery = searchQuery,
                onSearchChange = {
                    searchQuery = it
                }
            )

            Spacer(modifier = Modifier.height(18.dp))

            Card(

                shape = RoundedCornerShape(24.dp),

                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme
                            .colorScheme
                            .surfaceVariant
                )
            ) {

                Column(
                    modifier =
                        Modifier.padding(18.dp)
                ) {

                    Text(
                        text = "Create Note",
                        fontSize = 20.sp,
                        fontWeight =
                            FontWeight.SemiBold
                    )

                    Spacer(
                        modifier =
                            Modifier.height(14.dp)
                    )

                    OutlinedTextField(
                        value = title,

                        onValueChange = {

                            title = it
                            titleError = false
                        },

                        label = {
                            Text("Title")
                        },

                        isError = titleError,

                        supportingText = {

                            if (titleError) {

                                Text(
                                    text =
                                        "Title cannot be empty",

                                    color =
                                        MaterialTheme
                                            .colorScheme
                                            .error
                                )
                            }
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        shape =
                            RoundedCornerShape(18.dp)
                    )

                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )

                    OutlinedTextField(
                        value = description,

                        onValueChange = {

                            description = it
                            descriptionError = false
                        },

                        label = {
                            Text("Description")
                        },

                        isError =
                            descriptionError,

                        supportingText = {

                            if (descriptionError) {

                                Text(
                                    text =
                                        "Description cannot be empty",

                                    color =
                                        MaterialTheme
                                            .colorScheme
                                            .error
                                )
                            }
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        minLines = 3,

                        shape =
                            RoundedCornerShape(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            TabRow(
                selectedTabIndex = selectedTab
            ) {

                Tab(
                    selected = selectedTab == 0,

                    onClick = {
                        selectedTab = 0
                    },

                    text = {
                        Text("All Notes")
                    }
                )

                Tab(
                    selected = selectedTab == 1,

                    onClick = {
                        selectedTab = 1
                    },

                    text = {
                        Text("Favorites")
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (displayedNotes.isEmpty()) {

                Box(
                    modifier = Modifier.fillMaxSize(),

                    contentAlignment =
                        Alignment.Center
                ) {

                    Text(
                        text = "No Notes Found",
                        fontSize = 20.sp,
                        color =
                            MaterialTheme
                                .colorScheme
                                .primary
                    )
                }

            } else {

                LazyColumn(

                    verticalArrangement =
                        Arrangement.spacedBy(12.dp),

                    contentPadding =
                        PaddingValues(bottom = 100.dp)
                ) {

                    items(displayedNotes) { note ->

                        NoteCard(
                            note = note,

                            navController =
                                navController,

                            onDelete = {
                                viewModel.deleteNote(note)
                            },

                            onFavoriteToggle = {

                                note.isFavorite.value =
                                    !note.isFavorite.value
                            }
                        )
                    }
                }
            }
        }
    }
}