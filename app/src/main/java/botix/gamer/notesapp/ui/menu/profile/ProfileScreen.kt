package botix.gamer.notesapp.ui.menu.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import botix.gamer.notesapp.R
import java.lang.reflect.Modifier


@Composable
fun ProfileScreen(paddingValues: PaddingValues) {
    Box (
        modifier = androidx.compose.ui.Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(id = R.string.forgot_password_screen),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = androidx.compose.ui.Modifier.padding(
                start = 20.dp
            )
        )
    }
}