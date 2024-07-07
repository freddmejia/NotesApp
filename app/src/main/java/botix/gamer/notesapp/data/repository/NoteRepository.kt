package botix.gamer.notesapp.data.repository

import botix.gamer.notesapp.data.model.Note

interface NoteRepository {
    suspend fun createNote(title: String, note: String, status: String, userId: Int): Note?
    suspend fun updateNote(idNote: Int, title: String, note: String, status: String): Note?


}