package botix.gamer.notesapp.ui.component.bottom_bar

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import botix.gamer.notesapp.presentation.note.NoteViewModel
import botix.gamer.notesapp.ui.menu.profile.HomeScreen
import botix.gamer.notesapp.ui.menu.note.NoteScreen
import botix.gamer.notesapp.ui.menu.profile.ProfileScreen
import botix.gamer.notesapp.ui.navigation.BottomNavItem



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController) {


    Scaffold(
        bottomBar = {
            //BottomAppBar {
                BottomNavigationBar(
                    navController = navController
                )
            //}
        }
    ) { paddingValues ->
        NavigationScreens(
            navController = navController,
            paddingValues = paddingValues
        )
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navItems = listOf(BottomNavItem.Home, BottomNavItem.Note, BottomNavItem.Profile)
    var selectedItem by rememberSaveable { mutableStateOf(0) }


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
        composable(BottomNavItem.Home.path) { HomeScreen() }
        composable(BottomNavItem.Note.path) { NoteScreen(noteViewModel = NoteViewModel(), paddingValues = paddingValues) }
        composable(BottomNavItem.Profile.path) { ProfileScreen() }
    }
}