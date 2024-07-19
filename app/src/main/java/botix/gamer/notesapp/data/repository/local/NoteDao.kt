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

    @Query("update note set id = :id, updatedAt = :updatedAt where createdAt = :createdAt")
    fun updateNote(id: Int, updatedAt: String, createdAt: String)

    @Query("select * from note order by createdAt DESC")
    fun fetchNotesByStatus(): List<Note>

    /*@Query(update note set ti)
    fun createNote(note: Note)**/
}