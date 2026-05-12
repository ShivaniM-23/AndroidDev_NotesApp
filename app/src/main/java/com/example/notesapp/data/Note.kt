package com.example.notesapp.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Note(

    val id: Int,

    val title: String,

    val description: String,

    val createdAt: String,

    val isFavorite: MutableState<Boolean> =
        mutableStateOf(false)
)