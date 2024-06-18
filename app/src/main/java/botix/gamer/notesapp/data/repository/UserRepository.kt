package botix.gamer.notesapp.data.repository

import botix.gamer.notesapp.LoginMutation
import botix.gamer.notesapp.data.model.User
import botix.gamer.notesapp.di.AdminApolloClient
import botix.gamer.notesapp.utils.Result
import botix.gamer.notesapp.utils.Utility.Companion.errorResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


interface  UserRepository {
    suspend fun login(): User?
}
/*
class UserRepository @Inject constructor(
    private val apolloClient: AdminApolloClient
) {
    suspend fun loginUser(email: String, password: String): Result<User> {
        return withContext(Dispatchers.Default) {
            try {

                var user = User()
                Result.Success(user)
            }catch (e: Throwable){
                errorResult(e.message ?: e.toString())
            }

        }
    }
}*/