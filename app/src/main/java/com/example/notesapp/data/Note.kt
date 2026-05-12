package com.example.notesapp.data

data class Note(

    val id: Int,

    val title: String,

    val description: String,

    val createdAt: String,

    var isFavorite: Boolean = false
)