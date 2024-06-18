package botix.gamer.notesapp.data.model

data class User(var id: Int, var name: String, var email: String) {
    constructor(): this(
        id = 0, name = "", email = ""
    )
}