package botix.gamer.notesapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import botix.gamer.notesapp.ui.component.bottom_bar.BottomNavPath

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String, val path: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home", BottomNavPath.HOME.toString())
    object Note : BottomNavItem("note", Icons.Default.NoteAlt, "Note", BottomNavPath.NOTE.toString())
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile", BottomNavPath.PROFILE.toString())
}