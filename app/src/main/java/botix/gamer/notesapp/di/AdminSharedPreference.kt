package botix.gamer.notesapp.di

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import botix.gamer.notesapp.R

class AdminSharedPreference @Inject constructor(private val appContext: Context) {
    fun getSharedPreferences(): SharedPreferences {
        return appContext.applicationContext.getSharedPreferences(appContext.getString(R.string.shared_preferences), Context.MODE_PRIVATE)
    }
}