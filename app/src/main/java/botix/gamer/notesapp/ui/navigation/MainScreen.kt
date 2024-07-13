package botix.gamer.notesapp.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import botix.gamer.notesapp.data.model.SplashAuth
import botix.gamer.notesapp.presentation.account.AccountViewModel
import botix.gamer.notesapp.ui.component.bottom_bar.BottomNavigationBar
import botix.gamer.notesapp.ui.component.bottom_bar.TabNavigationBar
import botix.gamer.notesapp.ui.menu.home.HomeScreen
import botix.gamer.notesapp.ui.menu.note.NoteScreen
import botix.gamer.notesapp.ui.menu.profile.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreenV2(navBarController: NavHostController, accountViewModel: AccountViewModel) {
    Scaffold(
        topBar = {
            TabNavigationBar(title = "Note app")
        },
        bottomBar = {
            BottomNavigationBar(
                navBarController = navBarController
            )
        }
    ) { paddingValues ->
        NavigationScreens(
            navBarController = navBarController,
            paddingValues = paddingValues,
            accountViewModel = accountViewModel
        )
    }
}

@Composable
fun NavigationScreens(navBarController: NavHostController, paddingValues: PaddingValues, accountViewModel: AccountViewModel) {
    val resulSplashAuth: SplashAuth by accountViewModel.resulSplashAuth.collectAsState(initial = SplashAuth.Splash)

    LaunchedEffect(resulSplashAuth) {
        if (resulSplashAuth == SplashAuth.Menu) {
            navBarController.navigate(BottomNavItem.Home.path) {
                popUpTo(navBarController.graph.startDestinationId) { inclusive = true }
            }
        }
    }

    NavHost(navBarController, startDestination = BottomNavItem.Home.path) {
        composable(BottomNavItem.Home.path) { HomeScreen(paddingValues = paddingValues) }
        composable(BottomNavItem.Note.path) { NoteScreen(paddingValues = paddingValues) }
        composable(BottomNavItem.Profile.path) { ProfileScreen(paddingValues = paddingValues, accountViewModel = accountViewModel) }
    }
}