package botix.gamer.notesapp.ui.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import botix.gamer.notesapp.presentation.account.AccountViewModel

@Composable
fun LoginScreen(
    accountViewModel: AccountViewModel,
    registerOnClicked: () -> Unit,
    forgotPassOnClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ){
        LoginForm(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            accountViewModel = accountViewModel,
            registerOnClicked = registerOnClicked,
            forgotPassOnClicked = forgotPassOnClicked
        )
    }
}

@Composable
fun LoginForm(modifier: Modifier, accountViewModel: AccountViewModel, registerOnClicked: () -> Unit,
              forgotPassOnClicked: () -> Unit) {

    val email: String by accountViewModel.email.observeAsState(initial = "")
    val password: String by accountViewModel.password.observeAsState(initial = "")
    val loading: Boolean by accountViewModel.loading.observeAsState(initial = false)

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
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
            Spacer(modifier = Modifier.padding(20.dp))
            SimpleButtonLogin(
                text = stringResource(id = R.string.login_button),
                buttonColors = ButtonDefaults.buttonColors()
            ) {
                accountViewModel.launchRegister(isNewUser = true)
            }
            Spacer(modifier = Modifier.padding(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ForgotPasswordText(forgotPassOnClicked = {
                    forgotPassOnClicked()
                })
                NewUserText(registerOnClicked = {
                    registerOnClicked()
                })
            }
        }
    }
}



@Composable
fun PlaceHolderImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.place_holder_login) ,
        contentDescription ="header",
        modifier = modifier
            .padding(bottom = 10.dp),
        contentScale = ContentScale.Fit

    )
}
@Composable
fun EmailField(email: String, onTextFieldChange: (String) -> Unit) {
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
fun PasswordField(password: String, onTextFieldChange: (String) -> Unit) {
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
fun SimpleButtonLogin(text: String, buttonColors: ButtonColors, simpleOnClickAction: () -> Unit) {
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

@Composable
fun ForgotPasswordText(forgotPassOnClicked: () -> Unit) {
    Text(
        text = stringResource(id = R.string.forgot_password),
        modifier = Modifier.clickable {
            forgotPassOnClicked()
        }
    )
}


@Composable
fun NewUserText(registerOnClicked: () -> Unit) {
    Text(
        text = stringResource(id = R.string.new_user),
        modifier = Modifier.clickable {
            registerOnClicked()
        }
    )
}
@Preview
@Composable
fun SeeLogin() {
    LoginScreen(
        accountViewModel = AccountViewModel(),
        forgotPassOnClicked = {
            //
        },
        registerOnClicked = {
            //
        }
    )
}