package com.example.notesapp.repository

import androidx.compose.runtime.mutableStateListOf
import com.example.notesapp.data.Note

class NoteRepository {

    private val notes =
        mutableStateListOf<Note>()

    fun getNotes(): List<Note> {
        return notes
    }

    fun addNote(note: Note) {
        notes.add(note)
    }

    fun deleteNote(note: Note) {
        notes.remove(note)
    }

    fun updateNote(updatedNote: Note) {

        val index =
            notes.indexOfFirst {
                it.id == updatedNote.id
            }

        if (index != -1) {

            notes[index] = updatedNote
        }
    }
}