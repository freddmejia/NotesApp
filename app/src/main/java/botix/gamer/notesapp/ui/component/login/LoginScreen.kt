package botix.gamer.notesapp.ui.component.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import botix.gamer.notesapp.R
import botix.gamer.notesapp.presentation.account.LoginViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ){
        loginForm(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            loginViewModel = loginViewModel
        )
    }
}

@Composable
fun loginForm(modifier: Modifier, loginViewModel: LoginViewModel) {

    val email: String by loginViewModel.email.observeAsState(initial = "")
    val password: String by loginViewModel.password.observeAsState(initial = "")
    val loading: Boolean by loginViewModel.loading.observeAsState(initial = false)
    //var loading = true

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp)
                    .align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
        else {
            placeHolderImage(modifier = Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(16.dp))
            emailField(
                email = email,
                onTextFieldChange = {
                    loginViewModel.onLoginChange(email = it, password = password)
                }
            )

            Spacer(modifier = Modifier.padding(10.dp))
            passwordField(
                password = password,
                onTextFieldChange = {
                    loginViewModel.onLoginChange(email = email, password = it)
                }
            )
            Spacer(modifier = Modifier.padding(20.dp))
            simpleButtonLogin(
                text = stringResource(id = R.string.login_button),
                buttonColors = ButtonDefaults.buttonColors()
            ) {
                loginViewModel.launchRegister(isNewUser = true)
            }
        }
    }
}



@Composable
fun placeHolderImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.place_holder_login) ,
        contentDescription ="header",
        modifier = modifier
            .padding(bottom = 10.dp),
        contentScale = ContentScale.Fit

    )
}
@Composable
fun emailField(email: String, onTextFieldChange: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = { onTextFieldChange(it) },
        modifier = Modifier
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        placeholder = { Text(text =  stringResource(id = R.string.write_email)) },
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
        )
    )
}
@Composable
fun passwordField(password: String, onTextFieldChange: (String) -> Unit) {
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
        placeholder = { Text(text =  stringResource(id = R.string.write_password)) },
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
@Composable
fun simpleButtonLogin(text: String, buttonColors: ButtonColors, simpleOnClickAction: () -> Unit) {
    Button(onClick = { simpleOnClickAction() },
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp),
        colors = buttonColors
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview()
@Composable
fun seeLogin() {
    LoginScreen(loginViewModel = LoginViewModel())
}