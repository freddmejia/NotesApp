package botix.gamer.notesapp.utils

import okhttp3.ResponseBody
import org.json.JSONObject

class Utility {

    companion object{
        const val requiredPasswordLength = 8
        const val statusActive = "1"

        fun statusActive(): String {
            return statusActive
        }
        fun <T> errorResult(message: String,errorBody: ResponseBody? = null): Result<T> {
            //Timber.d(message)
            var mess_d = message
            if (errorBody != null) {
                val json = JSONObject(errorBody?.string())
                mess_d = json.getString("error")
            }
            return Result.Error(mess_d)
        }
    }
}