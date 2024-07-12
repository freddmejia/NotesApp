package botix.gamer.notesapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import botix.gamer.notesapp.presentation.account.AccountViewModel
import botix.gamer.notesapp.ui.account.AccountScreen
import botix.gamer.notesapp.ui.account.ForgotPasswordScreen
import botix.gamer.notesapp.ui.account.LoginScreen
import botix.gamer.notesapp.ui.account.RegisterScreen
import botix.gamer.notesapp.ui.account.ToolBarFootMatch
import botix.gamer.notesapp.ui.component.bottom_bar.MainScreen
import botix.gamer.notesapp.ui.menu.home.HomeScreen
import botix.gamer.notesapp.ui.menu.note.NoteScreen
import botix.gamer.notesapp.ui.menu.profile.ProfileScreen

@Composable
fun NoteAppScreenMenu(
    navController: NavHostController,
                      navBarController: NavHostController,
                      navAccountController: NavHostController,
                      accountViewModel: AccountViewModel
) {
    NavHost(navController = navController, startDestination = AccountMenu.SplashMenu.name) {
        composable(AccountMenu.SplashMenu.name) { SplashScreen(navController = navController, accountViewModel = accountViewModel) }
        composable(AccountMenu.AccountLogRegister.name) { AccountScreen(navController = navController, navAccountController = navAccountController, accountViewModel = accountViewModel) }
        composable(AccountMenu.MainMenu.name) { MainScreen(navController = navController, navBarController = navBarController) }



    }
}

@Composable
fun NoteAppScreenMenuV2(
    navController: NavHostController,
    navBarController: NavHostController,
    accountViewModel: AccountViewModel
) {

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AccountMenu.valueOf(
        backStackEntry?.destination?.route ?: AccountMenu.SplashMenu.name
    )

    val isAuthenticated by accountViewModel.isAuthenticated.observeAsState(false)

    LaunchedEffect(isAuthenticated) {
        if (isAuthenticated) {
            navController.navigate(AccountMenu.MainMenu.name) {
                popUpTo(AccountMenu.AccountLogRegister.name) { inclusive = true }
            }
        }
    }
    Scaffold(
        topBar = {
            ToolBarFootMatch(
                currentScreen = currentScreen,
                navigationBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }

    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = AccountMenu.SplashMenu.name,
            modifier = Modifier.padding( paddingValues)

        ){
            composable(AccountMenu.SplashMenu.name) {
                SplashScreen(
                    navController = navController,
                    accountViewModel = accountViewModel
                )
            }
            /*composable(AccountMenu.AccountLogRegister.name) {
                AccountScreen(
                    navController = navController,
                    navAccountController = navController,
                    accountViewModel = accountViewModel
                )
            }*/
            composable(AccountMenu.MainMenu.name) {
                MainScreen(
                    navController = navController,
                    navBarController = navBarController
                )
            }

            composable(
                route = AccountMenu.Login.name
            ){
                accountViewModel.resetResultLogin()
                LoginScreen(
                    accountViewModel = accountViewModel,
                    registerOnClicked = {
                        navController.navigate(AccountMenu.Register.name)
                    },
                    forgotPassOnClicked = {
                        navController.navigate(AccountMenu.ForgotPassword.name)

                    }
                )
            }
            composable(
                route = AccountMenu.Register.name
            ){
                accountViewModel.resetResultLogin()
                RegisterScreen(accountViewModel = accountViewModel)
            }
            composable(
                route = AccountMenu.ForgotPassword.name
            ){
                accountViewModel.resetResultLogin()
                ForgotPasswordScreen(accountViewModel = accountViewModel)
            }

        }
    }
}