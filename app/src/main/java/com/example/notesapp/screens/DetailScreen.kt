package com.example.notesapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notesapp.data.Note

@Composable
fun DetailScreen(
    id: Int,
    title: String,
    description: String,
    navController: NavController,
    notesList: MutableList<Note>
) {

    val context = LocalContext.current

    var updatedTitle by remember {
        mutableStateOf(title)
    }

    var updatedDescription by remember {
        mutableStateOf(description)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Text(
            text = "Note Details",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = updatedTitle,

            onValueChange = {
                updatedTitle = it
            },

            label = {
                Text("Edit Title")
            },

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = updatedDescription,

            onValueChange = {
                updatedDescription = it
            },

            label = {
                Text("Edit Description")
            },

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            horizontalArrangement =
                Arrangement.spacedBy(16.dp)
        ) {

            Button(
                onClick = {

                    val note =
                        notesList.find {
                            it.id == id
                        }

                    if (note != null) {

                        val index =
                            notesList.indexOf(note)

                        notesList[index] =
                            note.copy(
                                title = updatedTitle,
                                description = updatedDescription
                            )

                        Toast.makeText(
                            context,
                            "Note Updated",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    navController.popBackStack()
                }
            ) {

                Text("Update")
            }

            Button(
                onClick = {

                    val noteToDelete =
                        notesList.find {
                            it.id == id
                        }

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

                Text("Delete")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                navController.popBackStack()
            }
        ) {

            Text("Back")
        }
    }
}