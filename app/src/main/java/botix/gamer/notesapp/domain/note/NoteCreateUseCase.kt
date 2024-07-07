package botix.gamer.notesapp.domain.note

import botix.gamer.notesapp.data.model.Note
import botix.gamer.notesapp.data.repository.NoteRepositoryImplementation
import javax.inject.Inject

class NoteCreateUseCase @Inject constructor(
    private val noteRepositoryImplementation: NoteRepositoryImplementation
) {
    suspend fun execute(title: String, note: String, status: String, userId: Int): Note? {
        return noteRepositoryImplementation.createNote(
            title = title,
            note = note,
             status = status,
            userId = userId
        )
    }
}