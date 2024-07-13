package botix.gamer.notesapp.presentation.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import botix.gamer.notesapp.data.model.SplashAuth
import botix.gamer.notesapp.data.model.User
import botix.gamer.notesapp.domain.user.LoginUseCase
import botix.gamer.notesapp.domain.user.LogoutUseCase
import botix.gamer.notesapp.domain.user.RegisterUseCase
import botix.gamer.notesapp.domain.user.UpdateUserUseCase
import botix.gamer.notesapp.utils.CompositionObj
import botix.gamer.notesapp.utils.Result
import botix.gamer.notesapp.utils.Utility
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val logoutUseCase: LogoutUseCase
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

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _resulSplashAuth = MutableStateFlow(
        SplashAuth.Splash
    )
    val resulSplashAuth: StateFlow<SplashAuth> = _resulSplashAuth

    private val _resultLogin = MutableStateFlow<Result<CompositionObj<User, String>>>(Result.Empty)
    val resultLogin: StateFlow<Result<CompositionObj<User, String>>> = _resultLogin


    private val _canCreateAccount = MutableStateFlow(false)
    val canCreateAccount: StateFlow<Boolean> = _canCreateAccount
    fun resetResultLogin() {
        _resultLogin.value = Result.Empty
    }

    fun resetFormVars() {
        _name.value = ""
        _email.value = ""
        _password.value = ""
        _rPassword.value = ""

    }

    fun resetIsUpdateAccount() {
        _isUpdateAccount.value = false
    }
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
    private fun setState(state: SplashAuth) {
        _resulSplashAuth.value = state
    }

    fun launchLogin() = viewModelScope.launch {
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
            setState(
                state = SplashAuth.Menu
            )
            resetFormVars()
        }?: run {
            _resultLogin.value = Result.Error("")
            setState(
                state = SplashAuth.Login
            )
        }
        _loading.value = false
    }

    fun launchRegister() = viewModelScope.launch {
        _resultLogin.value = Result.Empty
        _loading.value = true
        val register = registerUseCase.execute(
            name = _name.value.toString(),
            email = _email.value.toString(),
            password = _password.value.toString(),
            rPassword = _rPassword.value.toString()
        )
        register?.let {
            _resultLogin.value = Result.Success(
                CompositionObj(it, "")
            )
            setState(
                state = SplashAuth.Menu
            )
            resetFormVars()
        }?: run {
            _resultLogin.value = Result.Error("")
            setState(
                state = SplashAuth.Login
            )
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

    fun logout() = viewModelScope.launch {
        _loading.value = true
        logoutUseCase.execute()
        delay(2000)
        setState(state = SplashAuth.Login)
        _loading.value = false

    }
    fun onNameChanged(name: String) {
        _name.value = name
        registerEnableButton()
    }
    fun onEmailChanged(email: String) {
        _email.value = email
        registerEnableButton()
    }
    fun onPasswordChanged(password: String) {
        _password.value = password
        registerEnableButton()
    }
    fun onRPasswordChanged(rPassword: String) {
        _rPassword.value = rPassword
        registerEnableButton()
    }

    private fun registerEnableButton() {
        _canCreateAccount.value = false
        val nameTemp = _name.value.toString()
        val emailTemp = _email.value.toString()
        val passwordTemp = _password.value.toString()
        val rPasswordTemp = _rPassword.value.toString()

        if (nameTemp.replace("\\s".toRegex(), "").isEmpty())
            return
        if (emailTemp.replace("\\s".toRegex(), "").isEmpty())
            return
        if (passwordTemp.replace("\\s".toRegex(), "").isEmpty())
            return
        if (rPasswordTemp.replace("\\s".toRegex(), "").isEmpty())
            return

        if (passwordTemp != rPasswordTemp)
            return

        if (passwordTemp.length < Utility.requiredPasswordLength)
            return

        /*if (isValidEmail(email = emailTemp))
            return*/
        _canCreateAccount.value = true
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        val pattern = Pattern.compile(emailRegex)
        return pattern.matcher(email).matches()
    }

}