package botix.gamer.notesapp.data.repository

import botix.gamer.notesapp.LoginMutation
import botix.gamer.notesapp.data.model.User
import botix.gamer.notesapp.di.AdminApolloClient
import org.json.JSONObject
import javax.inject.Inject

class UserRepositoryImplementation @Inject constructor(
    private val adminApolloClient: AdminApolloClient
): UserRepository {
    override suspend fun login(): User? {
        try {
            var user = User()
            val responseLogin = adminApolloClient.getApolloClient().mutation(
                LoginMutation(
                    email = "",
                    password = ""
                )
            ).execute()
            responseLogin?.data?.login?.let {
                user.id = it.user!!.id.toInt()
                user.name = it.user.name
                user.email = it.user.email
            }
            return user
        }
        catch (e: Throwable){
            return null
        }
    }

    fun saveUser(user: User, accesToken: String) {
        var json = JSONObject()
        json.put("id",user.id)
        json.put("name",user.name)
        json.put("email",user.email)
        json.put("accesToken",accesToken)

    }

}