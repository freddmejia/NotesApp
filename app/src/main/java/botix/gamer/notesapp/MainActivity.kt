package botix.gamer.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import botix.gamer.notesapp.presentation.account.AccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import botix.gamer.notesapp.data.model.User
import botix.gamer.notesapp.ui.navigation.NoteAppScreenMenu
import botix.gamer.notesapp.ui.navigation.NoteAppScreenMenuV2
import botix.gamer.notesapp.utils.CompositionObj

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val accountViewModel: AccountViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContent {



            //NoteScreen(noteViewModel = noteViewModel)
            val navController = rememberNavController()
            val navBarController = rememberNavController()
            val navAccountController = rememberNavController()
            //val resultLogin: Result<CompositionObj<User, String>> by accountViewModel.resultLogin.collectAsState(initial = botix.gamer.notesapp.utils.Result.Empty)
            /*MaterialTheme{
                Surface {
                    MyApp()
                }
            }*/

            NoteAppScreenMenuV2(
                navController = navController,
                navBarController = navBarController,
                accountViewModel = accountViewModel,
            )

            /*when(resultLogin) {
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
            }*/


        }
    }
}

