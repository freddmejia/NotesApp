package botix.gamer.notesapp.ui.menu.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import botix.gamer.notesapp.R
import botix.gamer.notesapp.data.model.User
import botix.gamer.notesapp.presentation.account.AccountViewModel
import botix.gamer.notesapp.ui.account.EmailField
import botix.gamer.notesapp.ui.account.NameField
import botix.gamer.notesapp.ui.account.PasswordField
import botix.gamer.notesapp.ui.account.SimpleButtonLogin
import botix.gamer.notesapp.utils.CompositionObj
import botix.gamer.notesapp.utils.Result


@Composable
fun ProfileScreen(paddingValues: PaddingValues, accountViewModel: AccountViewModel = hiltViewModel()) {


    val name: String by accountViewModel.name.observeAsState(initial = "")
    val email: String by accountViewModel.email.observeAsState(initial = "")
    val password: String by accountViewModel.password.observeAsState(initial = "")
    val loading: Boolean by accountViewModel.loading.observeAsState(initial = false)
    val resultLogin: Result<CompositionObj<User, String>> by accountViewModel.resultLogin.collectAsState(initial = Result.Empty)


    Box (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.padding(16.dp))
            NameField(
                name = name,
                onTextFieldChange = {
                    accountViewModel.onNameChanged(name = it)
                }
            )
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
            Spacer(modifier = Modifier.padding(30.dp))
            SimpleButtonLogin(
                text = stringResource(id = R.string.update_button),
                buttonColors = ButtonDefaults.buttonColors()
            ) {

            }

            Spacer(modifier = Modifier.padding(15.dp))
            SimpleButtonLogin(
                text = stringResource(id = R.string.close_session),
                buttonColors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF81C784),
                    contentColor = Color.White
                )
            ) {

            }
        }
    }
}