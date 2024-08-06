package botix.gamer.notesapp.domain.note

import botix.gamer.notesapp.data.model.Note
import botix.gamer.notesapp.data.repository.remote.NoteRepositoryImplementation
import javax.inject.Inject

class NoteUpdateUseCase @Inject constructor(
    private val noteRepositoryImplementation: NoteRepositoryImplementation
) {
    suspend fun execute(idNote: Int, title: String, note: String, status: String, createdAt: String): Note? {
        return noteRepositoryImplementation.updateNote(
            idNote = idNote,
            title = title,
            note = note,
            status = status,
            createdAt = createdAt
        )
    }

    suspend fun executeUpdate(note: Note): Note? {
        noteRepositoryImplementation.updateNoteLocal(note = note)
        return noteRepositoryImplementation.fetchNoteByCreatedAt(createdAt = note.createdAt)
    }

    suspend fun executeDeleteNote(note: Note){
        noteRepositoryImplementation.deleteNoteLocal(note = note)
    }
}