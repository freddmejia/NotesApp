package botix.gamer.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import botix.gamer.notesapp.presentation.account.AccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import botix.gamer.notesapp.data.model.SplashAuth
import botix.gamer.notesapp.data.model.User
import botix.gamer.notesapp.ui.account.AccountScreenV2
import botix.gamer.notesapp.ui.navigation.MainScreenV2
import botix.gamer.notesapp.ui.navigation.SplashScreenV2
import botix.gamer.notesapp.utils.CompositionObj

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val accountViewModel: AccountViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            loginPlatform(
            )
        }
    }
}

@Composable
fun loginPlatform(accountViewModel: AccountViewModel = hiltViewModel()) {
    val navBarController = rememberNavController()
    val navAccountController = rememberNavController()
    val resulSplashAuth: SplashAuth by accountViewModel.resulSplashAuth.collectAsState(initial = SplashAuth.Splash)

    LaunchedEffect(true) {
        accountViewModel.splashLogin()
    }

    when(resulSplashAuth) {
        SplashAuth.Splash -> {
            SplashScreenV2()
        }

        SplashAuth.Login -> {
            AccountScreenV2(
                navAccountController = navAccountController,
                accountViewModel = accountViewModel
            )
        }

        SplashAuth.Menu -> {
            MainScreenV2(
                navBarController = navBarController,
                accountViewModel = accountViewModel
            )
        }
    }
}