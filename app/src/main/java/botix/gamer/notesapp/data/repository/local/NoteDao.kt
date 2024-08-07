package botix.gamer.notesapp.data.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import botix.gamer.notesapp.data.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun createNote(note: Note)

    @Query("update note set id = :id, updatedAt = :updatedAt, note = :note, title = :title where createdAt = :createdAt")
    fun updateNote(id: Int, updatedAt: String, note: String, title: String, createdAt: String)

    @Query("select * from note order by updatedAt ASC")
    fun fetchNotes(): List<Note>

    @Query("delete from note  where createdAt = :createdAt")
    fun deleteNote(createdAt: String)

    @Query("select * from note where createdAt = :createdAt")
    fun fetchNoteByCreatedAt(createdAt: String): Note

    /*@Query(update note set ti)
    fun createNote(note: Note)**/
}