package botix.gamer.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import botix.gamer.notesapp.data.model.User
import botix.gamer.notesapp.presentation.account.AccountViewModel
import botix.gamer.notesapp.ui.account.AccountScreen
import botix.gamer.notesapp.ui.theme.NotesAppTheme
import botix.gamer.notesapp.ui.component.bottom_bar.MainScreen
import botix.gamer.notesapp.ui.account.LoginScreen
import botix.gamer.notesapp.utils.CompositionObj
import botix.gamer.notesapp.utils.Result

class MainActivity : ComponentActivity() {
    private val accountViewModel: AccountViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContent {


            //NoteScreen(noteViewModel = noteViewModel)
            val navController = rememberNavController()
            val resultLogin: Result<CompositionObj<User, String>> by accountViewModel.resultLogin.collectAsState(initial = Result.Empty)

            when(resultLogin) {
                is Result.Success -> {
                    MainScreen(navController = navController)
                }
                else  -> {
                    AccountScreen(
                        accountViewModel = accountViewModel
                    )
                    /*LoginScreen(
                        accountViewModel = AccountViewModel(),
                        forgotPassOnClicked = {
                            //
                        },
                        registerOnClicked = {
                            //
                        }
                    )*/
                }
            }


        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotesAppTheme {
        Greeting("Android")
    }
}