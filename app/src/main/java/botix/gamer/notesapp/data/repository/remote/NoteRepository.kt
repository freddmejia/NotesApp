package botix.gamer.notesapp.data.repository.remote

import botix.gamer.notesapp.data.model.Note

interface NoteRepository {
    suspend fun createNote(title: String, note: String, status: String, userId: Int, createdAt: String): Note?
    suspend fun updateNote(idNote: Int, title: String, note: String, status: String, createdAt: String): Note?
    suspend fun fechNotesByUserId(userId: Int, status: String): ArrayList<Note>
    suspend fun createNoteLocal(note: Note)
    suspend fun updateNoteLocal(note: Note)
    suspend fun fetchNotesLocal(): ArrayList<Note>
    suspend fun deleteNoteLocal(note: Note)

    suspend fun fetchNoteByCreatedAt(createdAt: String): Note?
}