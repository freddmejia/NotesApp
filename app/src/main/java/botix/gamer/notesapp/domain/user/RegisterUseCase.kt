package botix.gamer.notesapp.domain.user

import botix.gamer.notesapp.data.model.User
import botix.gamer.notesapp.data.reposuserLoginory.UserRepositoryImplementation
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepositoryImplementation: UserRepositoryImplementation
) {
    suspend fun execute(name: String, email: String, password: String, rPassword: String): User? {
        return userRepositoryImplementation.register(
            name = name,
            email = email,
            password = password,
            rPassword = rPassword
        )
    }

    fun userLogged(): Boolean {
        return userRepositoryImplementation.userLogged()
    }
}