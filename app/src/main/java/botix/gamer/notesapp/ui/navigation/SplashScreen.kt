package botix.gamer.notesapp.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import botix.gamer.notesapp.R
import botix.gamer.notesapp.data.model.User
import botix.gamer.notesapp.presentation.account.AccountViewModel
import botix.gamer.notesapp.utils.CompositionObj
import botix.gamer.notesapp.utils.Result
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController, accountViewModel: AccountViewModel) {
    val resultLogin: Result<CompositionObj<User, String>> by accountViewModel.resultLogin.collectAsState(initial = Result.Empty)
    accountViewModel.isLoggedUser()

    LaunchedEffect(Unit) {
        delay(2000) // Espera 2 segundos
        if (resultLogin is Result.Success) {
            navController.navigate(AccountMenu.MainMenu.name) {
                popUpTo(AccountMenu.SplashMenu.name) { inclusive = true }
            }
        }
        else {
            navController.navigate(AccountMenu.AccountLogRegister.name) {
                popUpTo(AccountMenu.SplashMenu.name) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        )
        {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Image(
                    painter = painterResource(id = R.drawable.placeholder_splash3) ,
                    contentDescription = "header",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.padding(
                        bottom = 20.dp
                    )
                )
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}
