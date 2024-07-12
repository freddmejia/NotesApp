package botix.gamer.notesapp.ui.menu.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import botix.gamer.notesapp.R
import botix.gamer.notesapp.data.model.User
import botix.gamer.notesapp.presentation.account.AccountViewModel
import botix.gamer.notesapp.presentation.account.UserViewModel
import botix.gamer.notesapp.ui.account.NameField
import botix.gamer.notesapp.ui.account.PasswordField
import botix.gamer.notesapp.ui.account.SimpleButtonLogin
import botix.gamer.notesapp.ui.navigation.AccountMenu

@Composable
fun ProfileScreen(
    navController: NavHostController,
    paddingValues: PaddingValues,
    accountViewModel: AccountViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {


    val name: String by accountViewModel.name.observeAsState(initial = "")
    val password: String by accountViewModel.password.observeAsState(initial = "")
    val loading: Boolean by accountViewModel.loading.observeAsState(initial = false)
    val isUpdateAccount: Boolean by accountViewModel.isUpdateAccount.observeAsState(initial = false)

    val user: User? by userViewModel.user.observeAsState(initial = null)

    LaunchedEffect(true) {
        userViewModel.loadUser()
        user?.let {
            accountViewModel.onEmailChanged(
                email = user!!.email
            )
            accountViewModel.onNameChanged(
                name = user!!.name
            )
        }
    }

    if (isUpdateAccount) {
        Toast.makeText(LocalContext.current, "Account updated", Toast.LENGTH_SHORT).show()
        accountViewModel.resetIsUpdateAccount()
    }

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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                        .height(64.dp)
                        .align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
            else {
                Spacer(modifier = Modifier.padding(16.dp))
                NameField(
                    name = name,
                    onTextFieldChange = {
                        accountViewModel.onNameChanged(name = it)
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

                if (user != null) {
                    SimpleButtonLogin(
                        text = stringResource(id = R.string.update_button),
                        buttonColors = ButtonDefaults.buttonColors()
                    ) {
                        accountViewModel.updateUser(
                            userId = user!!.id
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(15.dp))
                SimpleButtonLogin(
                    text = stringResource(id = R.string.close_session),
                    buttonColors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF81C784),
                        contentColor = Color.White
                    )
                ) {
                    navController.navigate(AccountMenu.Login.name) {
                        popUpTo(AccountMenu.MainMenu.name) { inclusive = false }
                    }
                }
            }

        }
    }
}