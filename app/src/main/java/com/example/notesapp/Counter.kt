package com.example.notesapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CounterScreen() {

    var count by remember { mutableStateOf(0) }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Count: $count",
                fontSize = 32.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row {

                Button(
                    onClick = { count-- },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("-")
                }

                Button(
                    onClick = { count++ },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("+")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { count = 0 }
            ) {
                Text("Reset")
            }
        }
    }
}