package botix.gamer.notesapp.domain.user

import botix.gamer.notesapp.data.reposuserLoginory.UserRepositoryImplementation
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepositoryImplementation: UserRepositoryImplementation
) {
    suspend fun execute(id: Int,
                        name: String,
                        email: String,
                        password: String,
                        rPassword: String): Boolean {
        return userRepositoryImplementation.updateUser(
            id = id,
            name = name,
            email = email,
            password = password,
            rPassword = rPassword
        )
    }

}