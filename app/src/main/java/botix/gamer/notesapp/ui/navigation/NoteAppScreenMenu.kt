package botix.gamer.notesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import botix.gamer.notesapp.presentation.account.AccountViewModel
import botix.gamer.notesapp.ui.account.AccountScreen
import botix.gamer.notesapp.ui.component.bottom_bar.MainScreen

@Composable
fun NoteAppScreenMenu(navController: NavHostController, navBarController: NavHostController, accountViewModel: AccountViewModel) {
    NavHost(navController = navController, startDestination = AccountMenu.SplashMenu.name) {
        composable(AccountMenu.SplashMenu.name) { SplashScreen(navController = navController, accountViewModel = accountViewModel) }
        composable(AccountMenu.AccountLogRegister.name) { AccountScreen(navController = navController, accountViewModel = accountViewModel) }
        composable(AccountMenu.MainMenu.name) { MainScreen(navController = navBarController) }
    }
}