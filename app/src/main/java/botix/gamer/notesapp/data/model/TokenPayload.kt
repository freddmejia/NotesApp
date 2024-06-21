package botix.gamer.notesapp.data.model

import org.json.JSONObject

data class TokenPayload(var accessToken: String, var refreshToken: String,
                        var expiresIn: Int, var tokenType: String
) {
    constructor(): this(
        accessToken = "", refreshToken = "", expiresIn = 0, tokenType = ""
    )
    constructor(jsonObject: JSONObject): this(
        jsonObject.getString("accessToken"),
        jsonObject.getString("refreshToken"),
        jsonObject.getInt("expiresIn"),
        jsonObject.getString("tokenType"),
    )
}