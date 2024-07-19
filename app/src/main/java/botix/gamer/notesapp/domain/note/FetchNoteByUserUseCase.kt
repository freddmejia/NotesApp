package botix.gamer.notesapp.domain.note

import botix.gamer.notesapp.data.model.Note
import botix.gamer.notesapp.data.repository.remote.NoteRepositoryImplementation
import javax.inject.Inject


class FetchNoteByUserUseCase @Inject constructor(
    private val noteRepositoryImplementation: NoteRepositoryImplementation
) {
    suspend fun execute(userId: Int, status: String):  ArrayList<Note>  {
        return noteRepositoryImplementation.fechNotesByUserId(
            userId = userId, status = status
        )
    }

    suspend fun executeFetchNotesLocal(): ArrayList<Note>{
        return noteRepositoryImplementation.fetchNotesLocal()
    }
}