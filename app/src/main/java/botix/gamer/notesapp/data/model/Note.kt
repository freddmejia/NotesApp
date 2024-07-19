package botix.gamer.notesapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var note: String,
    var createdAt: String,
    var updatedAt: String,
    var status: String
    ) {
    constructor(): this(
        id = 0,
        title = "",
        note = "",
        createdAt = "",
        updatedAt = "",
        status = ""
    ){

    }
}