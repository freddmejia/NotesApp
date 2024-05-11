package botix.gamer.notesapp.ui.navigation

import androidx.annotation.StringRes
import botix.gamer.notesapp.R


enum class AccountMenu(@StringRes val title: Int){
    Login(title = R.string.login),
    Register(title = R.string.register),
    MenuApp(title = R.string.app_name)
}