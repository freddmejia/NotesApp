package botix.gamer.notesapp.ui.navigation

import androidx.annotation.StringRes
import botix.gamer.notesapp.R


enum class AccountMenu(@StringRes val title: Int){
    Login(title = R.string.login),
    Register(title = R.string.register),
    MenuApp(title = R.string.app_name),

    MainMenu(title = R.string.main_menu),
    SplashMenu(title = R.string.splash_menu),
    AccountLogRegister(title = R.string.account_menu)
}
/*
    <string name="main_menu">Main</string>
    <string name="splash_menu">Splash</string>
    <string name="account_menu">Login</string>
 */