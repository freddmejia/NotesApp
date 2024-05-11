package botix.gamer.notesapp.ui.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import botix.gamer.notesapp.R
import botix.gamer.notesapp.presentation.account.AccountViewModel

@Composable
fun RegisterScreen(accountViewModel: AccountViewModel) {
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

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,

    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(64.dp)
                    .align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
        else {
            PlaceHolderImage(modifier = Modifier.align(Alignment.CenterHorizontally))
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
            SimpleButtonLogin(
                text = stringResource(id = R.string.register_button),
                buttonColors = ButtonDefaults.buttonColors()
            ) {
                accountViewModel.launchRegister(isNewUser = true)
            }
        }
    }
}

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
        colors = TextFieldDefaults.colors(
        )
    )
}

@Composable
fun RPasswordField(password: String, onTextFieldChange: (String) -> Unit) {
    var passwordFieldVisible by rememberSaveable {
        mutableStateOf(false)
    }

    TextField(
        value = password,
        onValueChange = { onTextFieldChange(it) },
        modifier = Modifier
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        placeholder = { Text(text =  stringResource(id = R.string.rewrite_password)) },
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(),
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
        }

    )
}
@Preview
@Composable
fun SeeRegister() {
    RegisterScreen(accountViewModel = AccountViewModel())
}