package botix.gamer.notesapp.ui.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import botix.gamer.notesapp.presentation.account.AccountViewModel
import botix.gamer.notesapp.R
@Composable
fun ForgotPasswordScreen(
    accountViewModel: AccountViewModel = hiltViewModel(),
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ){
        Text(text = stringResource(id = R.string.forgot_password_screen))
    }
}