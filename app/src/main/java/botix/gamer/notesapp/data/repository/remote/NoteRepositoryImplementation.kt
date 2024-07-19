package botix.gamer.notesapp.data.repository.remote

import botix.gamer.notesapp.FetchNotesbyUserIdQuery
import botix.gamer.notesapp.RegisterNoteMutation
import botix.gamer.notesapp.UpdateNoteMutation
import botix.gamer.notesapp.data.model.Note
import botix.gamer.notesapp.data.repository.local.NoteDao
import botix.gamer.notesapp.di.AdminApolloClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteRepositoryImplementation @Inject constructor(
    private val adminApolloClient: AdminApolloClient,
    private val noteDao: NoteDao
): NoteRepository {
    override suspend fun createNote(
        title: String,
        note: String,
        status: String,
        userId: Int,
        createdAt: String
    ): Note? {
        try {
            var noteObj : Note? = null
            val responseCreate = adminApolloClient.getApolloClient()
                .mutation(
                    RegisterNoteMutation(
                        title = title,
                        note = note,
                        status = status,
                        user_id = userId,
                        created_at = createdAt
                    )
                ).execute()

            responseCreate?.data?.registerNote?.let { noteCreated->
                noteObj = Note(
                    id =  noteCreated.id.toInt(),
                    title = noteCreated.title,
                    note = noteCreated.note,
                    createdAt = noteCreated.created_at.toString(),
                    updatedAt = noteCreated.updated_at.toString(),
                    status = noteCreated.status
                )
            }
            return noteObj
        }
        catch (e: Throwable){
            return null
        }
    }

    override suspend fun updateNote(
        idNote: Int,
        title: String,
        note: String,
        status: String
    ): Note? {
        try {
            var noteObj : Note? = null
            val responseCreate = adminApolloClient.getApolloClient()
                .mutation(
                    UpdateNoteMutation(
                        id = idNote.toString(),
                        title = title,
                        note = note,
                        status = status,
                        created_at = ""
                    )
                ).execute()

            responseCreate?.data?.updateNote?.let { notUpdated->
                noteObj = Note(
                    id =  notUpdated.id.toInt(),
                    title = notUpdated.title,
                    note = notUpdated.note,
                    createdAt = notUpdated.created_at.toString(),
                    updatedAt = notUpdated.updated_at.toString(),
                    status = notUpdated.status
                )
            }
            return noteObj
        }
        catch (e: Throwable){
            return null
        }
    }

    override suspend fun fechNotesByUserId(userId: Int, status: String): ArrayList<Note> {
        try {
            var notes : ArrayList<Note> = arrayListOf()
            val responseCreate = adminApolloClient.getApolloClient()
                .query(
                    FetchNotesbyUserIdQuery(
                        user_id = userId.toString(),
                        status = status
                    )
                ).execute()

            responseCreate?.data?.notes_by_id_user?.let { note->
                note.data.forEach { noteItem->
                    notes.add(
                        Note(
                            id =  noteItem.id.toInt(),
                            title = noteItem.title,
                            note = noteItem.note,
                            createdAt = noteItem.created_at.toString(),
                            updatedAt = noteItem.updated_at.toString(),
                            status = noteItem.status
                        )
                    )
                }

            }
            return notes
        }
        catch (e: Throwable){
            return arrayListOf()
        }
    }

    override suspend fun createNoteLocal(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao.createNote(note = note)
        }
    }

    override suspend fun updateNoteLocal(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao.updateNote(id = note.id, updatedAt = note.updatedAt, createdAt = note.createdAt)
        }
    }

    override suspend fun fetchNotesLocal(): ArrayList<Note> {
        return withContext(Dispatchers.IO){
            var listNotes = arrayListOf<Note>()
            try {
                listNotes = noteDao.fetchNotesByStatus() as ArrayList<Note>
            }catch (e: java.lang.Exception){
                listNotes
            }
            return@withContext listNotes
        }
    }

    override suspend fun deleteNoteLocal(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao.deleteNote(noteId = note.id)
        }
    }

}