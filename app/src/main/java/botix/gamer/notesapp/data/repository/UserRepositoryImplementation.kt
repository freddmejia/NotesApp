package botix.gamer.notesapp.data.reposuserLoginory

import botix.gamer.notesapp.LoginMutation
import botix.gamer.notesapp.RegisterMutation
import botix.gamer.notesapp.UpdatedUserMutation
import botix.gamer.notesapp.data.model.TokenPayload
import botix.gamer.notesapp.data.model.User
import botix.gamer.notesapp.data.repository.UserRepository
import botix.gamer.notesapp.di.AdminApolloClient
import botix.gamer.notesapp.di.AdminSharedPreference
import org.json.JSONObject
import javax.inject.Inject

class UserRepositoryImplementation @Inject constructor(
    private val adminApolloClient: AdminApolloClient,
    private val adminSharedPreference: AdminSharedPreference
): UserRepository {
    override suspend fun login(email: String, password: String): User? {
        try {
            var user : User? = null
            var tokenPayload: TokenPayload ? = null
            val responseLogin = adminApolloClient.getApolloClient().mutation(
                LoginMutation(
                    email = email,
                    password = password
                )
            ).execute()
            responseLogin?.data?.login?.let { userLogin ->
                user = User(
                    id = userLogin.user!!.id.toInt(),
                    name = userLogin.user.name,
                    email = userLogin.user.email
                )
                tokenPayload = TokenPayload(
                    accessToken = userLogin.access_token!!,
                    refreshToken = userLogin.refresh_token!!,
                    expiresIn = userLogin.expires_in!!,
                    tokenType = userLogin.token_type!!
                )
                saveUser(
                    user = user!!,
                    accessToken = userLogin.access_token.toString(),
                    tokenPayload = tokenPayload!!
                )
            }
            return user
        }
        catch (e: Throwable){
            return null
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String,
        rPassword: String
    ): User? {
        try {
            var user : User? = null
            var tokenPayload: TokenPayload ? = null
            val responseRegister = adminApolloClient.getApolloClient().mutation(
                RegisterMutation(
                    name = name,
                    email = email,
                    password = password,
                    password_confirmation = rPassword
                )
            ).execute()

            responseRegister?.data?.register?.let { userRegister ->
                user = User(
                    id = userRegister.tokens!!.user!!.id.toInt(),
                    name = userRegister.tokens!!.user!!.name,
                    email = userRegister.tokens!!.user!!.email
                )
                tokenPayload = TokenPayload(
                    accessToken = userRegister.tokens!!.access_token!!,
                    refreshToken = userRegister.tokens!!.refresh_token!!,
                    expiresIn = userRegister.tokens!!.expires_in!!,
                    tokenType = userRegister.tokens!!.token_type!!
                )
                saveUser(
                    user = user!!,
                    accessToken = userRegister.tokens!!.access_token.toString(),
                    tokenPayload = tokenPayload!!
                )
            }
            return user
        }
        catch (e: Throwable){
            return null
        }
    }

    override suspend fun updateUser(
        id: Int,
        name: String,
        email: String,
        password: String,
        rPassword: String
    ): Boolean {
        try {
            var updateUser  = false
            val responseRegister = adminApolloClient.getApolloClient().mutation(
                UpdatedUserMutation(
                    id = id,
                    name = name,
                    password = if (password.replace("\\s".toRegex(), "").isNotEmpty()) password else "null",
                    password_confirmation = rPassword
                )
            ).execute()

            responseRegister?.data?.updatedUser?.success?.let {
                if (it) {
                    updateUser = true
                    val user = User(
                        id = id,
                        name = name,
                        email = email
                    )
                    updatePreferencesUser(
                        user = user
                    )
                }

            }
            return updateUser
        }
        catch (e: Throwable){
            return false
        }
    }

    fun userLogged(): Boolean {
        var isLogged = false
        try {
            isLogged = adminSharedPreference.getSharedPreferences()!!.getBoolean("userIsLogged",false)
        }catch (e: java.lang.Exception){
            e.printStackTrace()
            isLogged = false
        }
        return isLogged

    }

    private fun saveUser(user: User, accessToken: String, tokenPayload: TokenPayload) {
        updatePreferencesUser(
            user = user
        )

        var jsonTokenPayload = JSONObject()
        jsonTokenPayload.put("accessToken",tokenPayload.accessToken)
        jsonTokenPayload.put("refreshToken",tokenPayload.refreshToken)
        jsonTokenPayload.put("expiresIn",tokenPayload.expiresIn)
        jsonTokenPayload.put("tokenType",tokenPayload.tokenType)

        with(adminSharedPreference.getSharedPreferences()?.edit()){
            this?.putString("tokenPayload",jsonTokenPayload.toString())
            this?.putBoolean("userIsLogged",true)
            this?.putString("accessToken",accessToken)
            this?.apply()
        }
    }

    private fun updatePreferencesUser(user: User) {
        var jsonUser = JSONObject()
        jsonUser.put("id",user.id)
        jsonUser.put("name",user.name)
        jsonUser.put("email",user.email)
        with(adminSharedPreference.getSharedPreferences()?.edit()) {
            this?.putString("user", jsonUser.toString())
            this?.apply()
        }
    }

}