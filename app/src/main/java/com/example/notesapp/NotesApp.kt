package com.example.notesapp

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//added by me
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items


data class Note(
    val title: String,
    val description: String
)
@Composable
fun NotesScreen() {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var titleError by remember { mutableStateOf(false) }
    var descError by remember { mutableStateOf(false) }

    val context = LocalContext.current

    // ✅ List to store notes
    val notesList = remember { mutableStateListOf<Note>() }

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

        // 🔴 Title Error
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

        // 🔴 Description Error
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

                    // ✅ Add note to list
                    notesList.add(Note(title, description))

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

        // ✅ Notes List UI
        LazyColumn {
            items(notesList) { note ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),

                    onClick = {
                        Toast.makeText(
                            context,
                            "Title: ${note.title}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                ) {

                    Column(modifier = Modifier.padding(12.dp)) {

                        Text(
                            text = note.title,
                            fontSize = 18.sp
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = note.description,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}