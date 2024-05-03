package botix.gamer.notesapp.data.model

data class Note(
    val id: Int,
    var title: String,
    var note: String,
    var createdAt: String,
    var updatedAt: String
    ) {
    constructor(): this(
        id = 0,
        title = "",
        note = "",
        createdAt = "",
        updatedAt = ""
    ){

    }
}