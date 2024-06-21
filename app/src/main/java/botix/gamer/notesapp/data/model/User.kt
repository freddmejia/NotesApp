package botix.gamer.notesapp.data.model

import org.json.JSONObject

data class User(var id: Int, var name: String, var email: String) {
    constructor(): this(
        id = 0, name = "", email = ""
    )
    constructor(jsonObject: JSONObject): this(
        jsonObject.getInt("id"),
        jsonObject.getString("name"),
        jsonObject.getString("email")
    )
}