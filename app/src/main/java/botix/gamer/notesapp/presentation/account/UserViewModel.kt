package botix.gamer.notesapp.presentation.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import botix.gamer.notesapp.data.model.User
import botix.gamer.notesapp.di.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val tokenManager: TokenManager
):  ViewModel() {
    private val _user = MutableLiveData<User?>(null)
    val user: LiveData<User?> = _user


    fun loadUser() {
        val user = tokenManager.fetchLocalUser()
        user?.let { user ->
            setUser(user = user)
        }
    }

    private fun setUser(user: User) {
        _user.value = user
    }

}