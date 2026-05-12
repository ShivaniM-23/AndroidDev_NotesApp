package com.example.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.notesapp.data.Note
import com.example.notesapp.repository.NoteRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteViewModel : ViewModel() {

    private val repository =
        NoteRepository()

    val notes =
        repository.getNotes()

    fun addNote(
        title: String,
        description: String
    ) {

        val currentTime =
            SimpleDateFormat(
                "dd MMM yyyy, hh:mm a",
                Locale.getDefault()
            ).format(Date())

        val note = Note(
            id = System.currentTimeMillis().toInt(),
            title = title,
            description = description,
            createdAt = currentTime
        )

        repository.addNote(note)
    }

    fun deleteNote(note: Note) {
        repository.deleteNote(note)
    }

    fun updateNote(note: Note) {
        repository.updateNote(note)
    }
}