package botix.gamer.notesapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import botix.gamer.notesapp.data.model.Note
import botix.gamer.notesapp.data.repository.local.NoteDao

@Database(entities =
[
    Note::class
], version = 1, exportSchema = false
)
abstract class DataModelDatabase: RoomDatabase() {
    //abstract val noteDao: NoteDao
    abstract fun noteDao(): NoteDao
    companion object {
        @Volatile private var instance: DataModelDatabase? = null
        private const val DB_NAME = "notes.db"
        fun getDatabase(context: Context): DataModelDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, DataModelDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}