package botix.gamer.notesapp.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import botix.gamer.notesapp.data.model.User
import botix.gamer.notesapp.presentation.account.AccountViewModel
import botix.gamer.notesapp.utils.CompositionObj
import botix.gamer.notesapp.utils.Result
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, accountViewModel: AccountViewModel) {
    val resultLogin: Result<CompositionObj<User, String>> by accountViewModel.resultLogin.collectAsState(initial = Result.Empty)

    LaunchedEffect(Unit) {
        delay(2000) // Espera 2 segundos
        /*if (resultLogin is Result.Success) {
            navController.navigate(AccountMenu.MainMenu.name) {
                popUpTo(AccountMenu.SplashMenu.name) { inclusive = true }
            }
        }
        else {
            navController.navigate(AccountMenu.AccountLogRegister.name) {
                popUpTo(AccountMenu.SplashMenu.name) { inclusive = true }
            }
        }*/
        navController.navigate(AccountMenu.MainMenu.name) {
            popUpTo(AccountMenu.SplashMenu.name) { inclusive = true }
        }
        /*navController.navigate("main") {
            popUpTo("splash") { inclusive = true }
        }*/
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Splash Screen", style = MaterialTheme.typography.bodyLarge)
    }
}