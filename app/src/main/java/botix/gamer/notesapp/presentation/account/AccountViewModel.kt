package botix.gamer.notesapp.presentation.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import botix.gamer.notesapp.data.model.User
import botix.gamer.notesapp.utils.CompositionObj
import botix.gamer.notesapp.utils.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _rPassword = MutableLiveData<String>()
    val rPassword: LiveData<String> = _rPassword

    private val _isNewUser = MutableLiveData<Boolean>()
    val isNewUser: LiveData<Boolean> = _isNewUser

    private val _isAuthenticated = MutableLiveData<Boolean>()
    val isAuthenticated: LiveData<Boolean> = _isAuthenticated

    private val _resultLogin = MutableStateFlow<Result<CompositionObj<User, String>>>(Result.Empty)
    val resultLogin: StateFlow<Result<CompositionObj<User, String>>> = _resultLogin

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    fun onLoginChange(email: String, password: String) {
        _email.value = email
        _password.value = password
    }

    fun launchRegister(isNewUser: Boolean) = viewModelScope.launch{
        _isNewUser.value = isNewUser
        _resultLogin.value = Result.Empty
        _loading.value = true
        delay(2000L)

        _resultLogin.value = Result.Success(
            CompositionObj(User(0,"",""), "")
        )
        _isAuthenticated.value = true
        _loading.value = false
    }

    fun onNameChanged(name: String) {
        _name.value = name
    }
    fun onEmailChanged(email: String) {
        _email.value = email
    }
    fun onPasswordChanged(password: String) {
        _password.value = password
    }
    fun onRPasswordChanged(rPassword: String) {
        _rPassword.value = rPassword
    }

}