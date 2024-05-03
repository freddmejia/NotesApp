package botix.gamer.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import botix.gamer.notesapp.data.model.User
import botix.gamer.notesapp.ui.component.bottom_bar.MainScreen
import botix.gamer.notesapp.ui.component.login.LoginScreen
import botix.gamer.notesapp.ui.theme.NotesAppTheme
import botix.gamer.notesapp.presentation.LoginViewModel
import botix.gamer.notesapp.utils.CompositionObj
import botix.gamer.notesapp.utils.Result

class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContent {
            val navController = rememberNavController()
            val isAuthenticated: Boolean by loginViewModel.isAuthenticated.observeAsState(initial = false)
            val resultLogin: Result<CompositionObj<User, String>> by loginViewModel.resultLogin.collectAsState(initial = Result.Empty)

            when(resultLogin) {
                is Result.Success -> {
                    MainScreen(navController = navController)
                }
                else  -> {
                    LoginScreen(loginViewModel = loginViewModel)
                }
            }

            /*if (isAuthenticated) {
                MainScreen(navController = navController)
            }
            else {
                LoginScreen(loginViewModel = loginViewModel)
            }*/
            //val navController = rememberNavController()
            //MainScreen(navController = navController)
            /*NotesAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(loginViewModel = LoginViewModel())
                    //Greeting("Android")
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