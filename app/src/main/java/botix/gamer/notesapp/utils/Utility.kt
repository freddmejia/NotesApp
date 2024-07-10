package botix.gamer.notesapp.utils

import android.content.Context
import android.util.Log
import okhttp3.ResponseBody
import org.json.JSONObject
import botix.gamer.notesapp.R
import botix.gamer.notesapp.data.model.User

class Utility {
    companion object{
        fun <T> errorResult(message: String,errorBody: ResponseBody? = null): Result<T> {
            //Timber.d(message)
            var mess_d = message
            if (errorBody != null) {
                val json = JSONObject(errorBody?.string())
                mess_d = json.getString("error")
            }
            return Result.Error(mess_d)
        }

        fun fetchLocalUser(context: Context): User? {
            try {
                val sharedPreferences = context.getSharedPreferences(context.resources.getString(R.string.shared_preferences), Context.MODE_PRIVATE)
                Log.e("", "fetchLocalUser: "+sharedPreferences.getString("accessToken","") )
                return User(JSONObject(sharedPreferences!!.getString("user","")))
            }
            catch (e: java.lang.Exception){
                return null
            }
        }
    }
}