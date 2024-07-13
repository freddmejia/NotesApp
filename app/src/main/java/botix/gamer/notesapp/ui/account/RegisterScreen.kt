package botix.gamer.notesapp.ui.account

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import botix.gamer.notesapp.R
import botix.gamer.notesapp.data.model.User
import botix.gamer.notesapp.presentation.account.AccountViewModel
import botix.gamer.notesapp.utils.CompositionObj
import botix.gamer.notesapp.utils.Result
import botix.gamer.notesapp.utils.Utility

@Composable
fun RegisterScreen(
    accountViewModel: AccountViewModel  = hiltViewModel(),
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ){
        RegisterForm(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            accountViewModel = accountViewModel
        )
    }
}


@Composable
fun RegisterForm(modifier: Modifier, accountViewModel: AccountViewModel) {

    val name: String by accountViewModel.name.observeAsState(initial = "")
    val email: String by accountViewModel.email.observeAsState(initial = "")
    val password: String by accountViewModel.password.observeAsState(initial = "")
    val rPassword: String by accountViewModel.rPassword.observeAsState(initial = "")
    val loading: Boolean by accountViewModel.loading.observeAsState(initial = false)

    val resultLogin: Result<CompositionObj<User, String>> by accountViewModel.resultLogin.collectAsState(initial = Result.Empty)
    val canCreateAccount: Boolean by accountViewModel.canCreateAccount.collectAsState(initial = false)

    /*
    accountViewModel.onNameChanged(name = "joselu@gmai1l.com")
    accountViewModel.onEmailChanged(email = "")
    accountViewModel.onPasswordChanged(password = "12121212121")
    accountViewModel.onRPasswordChanged(rPassword = "12121212121")*/

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,

    ) {

        if (resultLogin is Result.Error) {
            Toast.makeText(LocalContext.current, R.string.invalid_passwords, Toast.LENGTH_SHORT).show()
            accountViewModel.resetResultLogin()
        }

        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(64.dp)
                    .align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.secondary,
                //trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
        else {
            PlaceHolderImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 250.dp)
                    .padding(bottom = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.padding(16.dp))
            NameField(
                name = name,
                onTextFieldChange = {
                    accountViewModel.onNameChanged(name = it)
                }
            )
            Spacer(modifier = Modifier.padding(10.dp))
            EmailField(
                email = email,
                onTextFieldChange = {
                    accountViewModel.onEmailChanged(email = it)
                }
            )

            Spacer(modifier = Modifier.padding(10.dp))
            PasswordField(
                password = password,
                onTextFieldChange = {
                    accountViewModel.onPasswordChanged(password = it)
                }
            )
            Spacer(modifier = Modifier.padding(10.dp))
            RPasswordField(
                password = rPassword,
                onTextFieldChange = {
                    accountViewModel.onRPasswordChanged(rPassword = it)
                }
            )
            Spacer(modifier = Modifier.padding(20.dp))
            if (canCreateAccount) {
                SimpleButtonLogin(
                    text = stringResource(id = R.string.register_button),
                    buttonColors = ButtonDefaults.buttonColors()
                ) {
                    accountViewModel.launchRegister()
                    //accountViewModel.launchRegister(isNewUser = true)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameField(name: String, onTextFieldChange: (String) -> Unit) {
    TextField(
        value = name,
        onValueChange = { onTextFieldChange(it) },
        modifier = Modifier
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        placeholder = { Text(text =  stringResource(id = R.string.write_name)) },
        singleLine = true,
        maxLines = 1,
        /*colors = TextFieldDefaults.colors(
        )*/
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RPasswordField(password: String, onTextFieldChange: (String) -> Unit) {
    var passwordFieldVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var shortPassword by rememberSaveable { mutableStateOf(false) }

    TextField(
        value = password,
        onValueChange = {
            onTextFieldChange(it)
            //improve this so that it captures from a function, not from a variable
            shortPassword = it.length < Utility.requiredPasswordLength
                        },
        modifier = Modifier
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        maxLines = 1,
        visualTransformation = if (passwordFieldVisible) VisualTransformation.None else PasswordVisualTransformation(mask = '*'),
        trailingIcon = {
            val image = if (passwordFieldVisible)
                Icons.Filled.Visibility
            else
                Icons.Filled.VisibilityOff
            val description = if(passwordFieldVisible) "Hide password" else "Show password"
            IconButton(onClick = {passwordFieldVisible = !passwordFieldVisible}) {
                Icon(imageVector = image, contentDescription = description)
            }
        },
        label = {
            if (shortPassword) {
                Text(text = stringResource(id = R.string.short_password))
            }
            else {
                Text(text =  stringResource(id = R.string.write_password))
            }
        },
        isError = shortPassword
    )
}
