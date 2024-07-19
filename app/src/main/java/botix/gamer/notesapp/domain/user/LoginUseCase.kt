package botix.gamer.notesapp.domain.user

import botix.gamer.notesapp.data.model.User
import botix.gamer.notesapp.data.repository.remote.UserRepositoryImplementation
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepositoryImplementation: UserRepositoryImplementation
) {
    suspend fun execute(email: String, password: String): User ? {
        return userRepositoryImplementation.login(
            email = email,
            password = password
        )
    }

    fun userLogged(): Boolean {
        return userRepositoryImplementation.userLogged()
    }
}