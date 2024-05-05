package botix.gamer.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import botix.gamer.notesapp.ui.theme.NotesAppTheme
import botix.gamer.notesapp.presentation.account.LoginViewModel
import botix.gamer.notesapp.presentation.note.NoteViewModel
import botix.gamer.notesapp.ui.menu.note.NoteScreen

class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val noteViewModel: NoteViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContent {

            NoteScreen(noteViewModel = noteViewModel)
            /*val navController = rememberNavController()
            val resultLogin: Result<CompositionObj<User, String>> by loginViewModel.resultLogin.collectAsState(initial = Result.Empty)

            when(resultLogin) {
                is Result.Success -> {
                    MainScreen(navController = navController)
                }
                else  -> {
                    LoginScreen(loginViewModel = loginViewModel)
                }
            }*/


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