package botix.gamer.notesapp.presentation.account

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import botix.gamer.notesapp.data.model.SplashAuth
import botix.gamer.notesapp.data.model.User
import botix.gamer.notesapp.di.TokenManager
import botix.gamer.notesapp.domain.user.LoginUseCase
import botix.gamer.notesapp.domain.user.RegisterUseCase
import botix.gamer.notesapp.domain.user.UpdateUserUseCase
import botix.gamer.notesapp.utils.CompositionObj
import botix.gamer.notesapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    tokenManager: TokenManager
) : ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _rPassword = MutableLiveData<String>()
    val rPassword: LiveData<String> = _rPassword

    private val _isUpdateAccount = MutableLiveData<Boolean>()
    val isUpdateAccount: LiveData<Boolean> = _isUpdateAccount

    private val _isAuthenticated = MutableLiveData<Boolean>()
    val isAuthenticated: LiveData<Boolean> = _isAuthenticated

    private val _resultLogin = MutableStateFlow<Result<CompositionObj<User, String>>>(Result.Empty)
    val resultLogin: StateFlow<Result<CompositionObj<User, String>>> = _resultLogin

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _resulSplashAuth = MutableStateFlow(
        SplashAuth.Splash
    )
    val resulSplashAuth: StateFlow<SplashAuth> = _resulSplashAuth

    fun resetIsUpdateAccount() {
        _isUpdateAccount.value = false
    }
    fun onLoginChange(email: String, password: String) {
        _email.value = email
        _password.value = password
    }

    fun isLoggedUser2() = isAuthenticated

    fun splashLogin() = viewModelScope.launch {
        _resulSplashAuth.value = SplashAuth.Splash
        val isLogged = loginUseCase.userLogged()
        delay(2000)
        if (isLogged){
            _resulSplashAuth.value = SplashAuth.Menu
        }
        else {
            _resulSplashAuth.value = SplashAuth.Login
        }
    }
    fun setState() {

    }

    fun resetResultLogin() {
        _resultLogin.value = Result.Empty
    }
    fun isLoggedUser() {
        _isAuthenticated.value = false
        _resultLogin.value = Result.Empty
        val isLogged = loginUseCase.userLogged()
        if ( !isLogged )
            return
        _resultLogin.value = Result.Success(
            CompositionObj(User(), "")
        )
        _isAuthenticated.value = true
        Log.e("", "launchLogin isLoggedUser viewmodel: "+loginUseCase.userLogged().toString() )
    }

    fun launchLogin2() = viewModelScope.launch {
        _isAuthenticated.value = false
        _resultLogin.value = Result.Empty
        _loading.value = true
        val login = loginUseCase.execute(
            email = _email.value.toString(),
            password = _password.value.toString()
        )
        login?.let {
            _resultLogin.value = Result.Success(
                CompositionObj(it, "")
            )
            //_isAuthenticated.value = true
        }?: run {
            //_isAuthenticated.value = false
            _resultLogin.value = Result.Error("")
        }
        _resultLogin.value = Result.Success(
            CompositionObj(User(), "")
        )
        _loading.value = false
        _isAuthenticated.value = true
    }
    fun launchLogin() = viewModelScope.launch {
        _isAuthenticated.value = false
        _resultLogin.value = Result.Empty
        _loading.value = true
        val login = loginUseCase.execute(
            email = _email.value.toString(),
            password = _password.value.toString()
        )
        login?.let {
            _resultLogin.value = Result.Success(
                CompositionObj(it, "")
            )
            _isAuthenticated.value = true
        }?: run {
            _isAuthenticated.value = false
            _resultLogin.value = Result.Error("")
        }
        _loading.value = false
    }

    fun launchRegister() = viewModelScope.launch {
        _isAuthenticated.value = false
        _resultLogin.value = Result.Empty
        _loading.value = true
        val login = registerUseCase.execute(
            name = _name.value.toString(),
            email = _email.value.toString(),
            password = _password.value.toString(),
            rPassword = _rPassword.value.toString()
        )
        login?.let {
            _resultLogin.value = Result.Success(
                CompositionObj(it, "")
            )
            _isAuthenticated.value = true
        }?: run {
            _isAuthenticated.value = false
            _resultLogin.value = Result.Error("")
        }
        _loading.value = false
    }

    fun updateUser(userId: Int) = viewModelScope.launch {
        _loading.value = true
        _isUpdateAccount.value = updateUserUseCase.execute(
            id = userId,
            name = _name.value.toString(),
            email = _email.value.toString(),
            password = _password.value.toString(),
            rPassword = _rPassword.value.toString()
        )

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