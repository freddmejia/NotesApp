package botix.gamer.notesapp.domain.note

import botix.gamer.notesapp.data.model.Note
import botix.gamer.notesapp.data.repository.NoteRepositoryImplementation
import javax.inject.Inject

class NoteUpdateUseCase @Inject constructor(
    private val noteRepositoryImplementation: NoteRepositoryImplementation
) {
    suspend fun execute(idNote: Int, title: String, note: String, status: String): Note? {
        return noteRepositoryImplementation.updateNote(
            idNote = idNote,
            title = title,
            note = note,
            status = status,
        )
    }
}