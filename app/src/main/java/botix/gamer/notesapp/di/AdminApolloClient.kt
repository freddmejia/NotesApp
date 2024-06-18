package botix.gamer.notesapp.di

import botix.gamer.notesapp.utils.Hosts
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient
import java.util.logging.Level
import java.util.logging.Logger

class AdminApolloClient {
    fun getApolloClient(): ApolloClient {
        val okHttpClient = OkHttpClient.Builder().build()
        Logger.getLogger(OkHttpClient::class.java.name).level = Level.FINE

        return ApolloClient.Builder()
            .serverUrl(Hosts.hostServer())
            .httpExposeErrorBody(true)
            .okHttpClient(okHttpClient)
            .build()
    }
}