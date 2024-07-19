package botix.gamer.notesapp.domain.user

import botix.gamer.notesapp.data.repository.remote.UserRepositoryImplementation
import javax.inject.Inject


class LogoutUseCase @Inject constructor(
    private val userRepositoryImplementation: UserRepositoryImplementation
) {
    suspend fun execute(){
        return userRepositoryImplementation.logout()
    }
}