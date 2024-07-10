package botix.gamer.notesapp.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import botix.gamer.notesapp.R
import botix.gamer.notesapp.data.model.User
import botix.gamer.notesapp.utils.Hosts
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONObject
import java.util.logging.Level
import java.util.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

class AdminApolloClient @Inject constructor(private val okHttpClient: OkHttpClient){
    fun getApolloClient(): ApolloClient {
        //val okHttpClient = OkHttpClient.Builder().build()
        Logger.getLogger(OkHttpClient::class.java.name).level = Level.FINE

        return ApolloClient.Builder()
            .serverUrl(Hosts.hostServer())
            .httpExposeErrorBody(true)
            .okHttpClient(okHttpClient)
            .build()
    }
}

@Singleton
class TokenManager @Inject constructor(private val preferences: SharedPreferences) {

    companion object {
        private const val TOKEN_KEY = "accessToken"
    }

    fun getToken(): String? {
        return preferences.getString(TOKEN_KEY, null)
    }
    fun fetchLocalUser(): User? {
        try {
            return User(JSONObject(preferences!!.getString("user","")))
        }
        catch (e: java.lang.Exception){
            return null
        }
    }

}


class AuthInterceptor @Inject constructor(private val tokenManager: TokenManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenManager.getToken()
        if (token != null) {
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            return chain.proceed(request)
        }
        return chain.proceed(chain.request())
    }

}