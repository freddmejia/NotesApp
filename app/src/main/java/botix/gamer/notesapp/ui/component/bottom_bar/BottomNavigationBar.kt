package botix.gamer.notesapp.ui.component.bottom_bar

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import botix.gamer.notesapp.ui.menu.profile.HomeScreen
import botix.gamer.notesapp.ui.menu.note.NoteScreen
import botix.gamer.notesapp.ui.menu.profile.ProfileScreen
import botix.gamer.notesapp.ui.navigation.BottomNavItem
import botix.gamer.notesapp.R

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TabNavigationBar(title = "Note app")
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController
            )
        }
    ) { paddingValues ->
        NavigationScreens(
            navController = navController,
            paddingValues = paddingValues
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun TabNavigationBar(title: String) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        actions = {
            /*IconButton(
                onClick = {
                    Toast.makeText(LocalContext.current, R.string.forgot_password_screen, Toast.LENGTH_SHORT).show()
                }
            ) {
                Icon(Icons.Default.Notifications, contentDescription = "Notification")
            }*/
        }
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navItems = listOf(BottomNavItem.Home, BottomNavItem.Note, BottomNavItem.Profile)
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar(
        modifier = Modifier
            .windowInsetsPadding(
                WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)
            )
    ) {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                alwaysShowLabel = true,
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.path) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
@Composable
fun NavigationScreens(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(navController, startDestination = BottomNavItem.Home.path) {
        composable(BottomNavItem.Home.path) { HomeScreen(paddingValues = paddingValues) }
        composable(BottomNavItem.Note.path) { NoteScreen(paddingValues = paddingValues) }
        composable(BottomNavItem.Profile.path) { ProfileScreen(paddingValues = paddingValues) }
    }
}