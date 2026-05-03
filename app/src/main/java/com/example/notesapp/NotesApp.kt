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

@Composable
fun NotesScreen() {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var titleError by remember { mutableStateOf(false) }
    var descError by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Notes App",
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Title Field
        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
                titleError = false
            },
            label = { Text("Enter Title") },
            isError = titleError
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Description Field
        OutlinedTextField(
            value = description,
            onValueChange = {
                description = it
                descError = false
            },
            label = { Text("Enter Description") },
            isError = descError
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                // Validation
                if (title.isBlank()) {
                    titleError = true
                }

                if (description.isBlank()) {
                    descError = true
                }

                if (title.isNotBlank() && description.isNotBlank()) {

                    Toast.makeText(
                        context,
                        "Title: $title\nDesc: $description",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Clear inputs
                    title = ""
                    description = ""
                }
            }
        ) {
            Text("Add Note")
        }
    }
}