package botix.gamer.notesapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _isNewUser = MutableLiveData<Boolean>()
    val isNewUser: LiveData<Boolean> = _isNewUser
    fun onLoginChange(email: String, password: String) {
        _email.value = email
        _password.value = password
    }

    fun launchRegister(isNewUser: Boolean){
        _isNewUser.value = isNewUser
    }

    fun onRegisterChange(name: String, email: String, password: String) {
        _name.value = name
        _email.value = email
        _password.value = password
    }
}