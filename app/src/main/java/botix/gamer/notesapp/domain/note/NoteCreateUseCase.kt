package botix.gamer.notesapp.domain.note

import botix.gamer.notesapp.data.model.Note
import botix.gamer.notesapp.data.repository.remote.NoteRepositoryImplementation
import javax.inject.Inject

class NoteCreateUseCase @Inject constructor(
    private val noteRepositoryImplementation: NoteRepositoryImplementation
) {
    suspend fun execute(note: Note, userId: Int): Note? {
        //executeLocal(note = note)
        return noteRepositoryImplementation.createNote(
            title = note.title,
            note = note.note,
            status = note.status,
            userId = userId,
            createdAt = note.createdAt
        )
    }

    suspend fun executeLocal(note: Note): Note? {
        noteRepositoryImplementation.createNoteLocal(
            note = note
        )
        return noteRepositoryImplementation.fetchNoteByCreatedAt(createdAt = note.createdAt)
    }
}