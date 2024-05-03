package botix.gamer.notesapp.ui.menu.note

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import botix.gamer.notesapp.data.model.Note


@Composable
fun NoteScreen() {
    val notes = listOf<Note>(
        Note(0,"DOCTORADO INVESTIGACION","Reuniones","24/4/2024","24/4/2024"),
        Note(0,"COMPRAR","Leche","24/4/2024","24/4/2024"),
        Note(0,"lol","videojuegos","24/4/2024","24/4/2024"),
        Note(0,"Que es ser desarrolador Senior?","Preguntar a todos","24/4/2024","24/4/2024"),
        Note(0,"Valencia playa","Reuniones","24/4/2024","24/4/2024"),
        Note(0,"Keys","Aws","24/4/2024","24/4/2024"),
        )
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1F)
                .padding(bottom = 10.dp)
        ) {
            items(notes) {note ->
                noteItem(note = note)
            }
        }
        Button(
            modifier = Modifier
                .padding(all = 20.dp)
                .align(Alignment.End),
            onClick = {
                
            }
        ) {
            Row(
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }

        }
    }

}
@Composable
fun noteItem(note : Note) {
    OutlinedCard (
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp)
    ){
        Column(
            modifier = Modifier.padding(all = 10.dp)
        ) {
            Text(
                text = note.title,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
            Row (
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = note.createdAt,
                    modifier = Modifier.padding(end = 10.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = note.note,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview
@Composable
fun previewL() {
    NoteScreen()
}