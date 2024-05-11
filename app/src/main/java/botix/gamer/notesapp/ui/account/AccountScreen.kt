package botix.gamer.notesapp.ui.account

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import botix.gamer.notesapp.ui.navigation.AccountMenu
import botix.gamer.notesapp.R
import botix.gamer.notesapp.presentation.account.AccountViewModel

@Composable
fun AccountScreen(
    navController: NavHostController = rememberNavController(),
    accountViewModel: AccountViewModel
) {

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AccountMenu.valueOf(
        backStackEntry?.destination?.route ?: AccountMenu.Login.name
    )
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
            startDestination = AccountMenu.Login.name,
            modifier = Modifier.padding( paddingValues)

        ) {
            composable(
                route = AccountMenu.Login.name
            ){
                LoginScreen(
                    accountViewModel = accountViewModel,
                    registerOnClicked = {
                        navController.navigate(AccountMenu.Register.name)
                    },
                    forgotPassOnClicked = {
                        navController.navigate(AccountMenu.Register.name)
                    }
                )
            }
            composable(
                route = AccountMenu.Register.name
            ){
                RegisterScreen(accountViewModel = accountViewModel)
            }
        }
    }
}

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
fun ToolBarFootMatch(
    currentScreen: AccountMenu,
    navigationBack: Boolean,
    navigateUp: ()-> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.TopAppBar(
        title = {
            Text(
                text = stringResource(id = currentScreen.title),
                color = colorResource(id = R.color.black),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        /*colors = TopAppBarDefaults.largeTopAppBarColors(
        containerColor = colorResource(id = R.color.aqua_island),
    ),*/
        modifier = modifier,
        navigationIcon = {
            if (navigationBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = ""
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun SeeAccountScreen() {
    AccountScreen(accountViewModel = AccountViewModel())
}