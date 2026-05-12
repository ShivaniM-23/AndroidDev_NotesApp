package com.example.notesapp.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchBar(
    searchQuery: String,
    onSearchChange: (String) -> Unit
) {

    OutlinedTextField(
        value = searchQuery,

        onValueChange = {
            onSearchChange(it)
        },

        label = {
            Text("Search Notes")
        },

        modifier = Modifier.fillMaxWidth()
    )
}