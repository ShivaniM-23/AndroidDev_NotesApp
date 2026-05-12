package com.example.notesapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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

    Scaffold(
        containerColor =
            MaterialTheme.colorScheme.background
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {

            Text(
                text = "Edit Note",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = updatedTitle,

                onValueChange = {
                    updatedTitle = it
                },

                label = {
                    Text("Title")
                },

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(20.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = updatedDescription,

                onValueChange = {
                    updatedDescription = it
                },

                label = {
                    Text("Description")
                },

                modifier = Modifier.fillMaxWidth(),

                minLines = 6,

                shape = RoundedCornerShape(20.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

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
                                description =
                                    updatedDescription
                            )

                        Toast.makeText(
                            context,
                            "Note Updated",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    navController.popBackStack()
                },

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(18.dp)
            ) {

                Text(
                    text = "Update Note",

                    modifier =
                        Modifier.padding(vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedButton(

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
                },

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(18.dp)
            ) {

                Text(
                    text = "Delete Note",

                    modifier =
                        Modifier.padding(vertical = 6.dp)
                )
            }
        }
    }
}