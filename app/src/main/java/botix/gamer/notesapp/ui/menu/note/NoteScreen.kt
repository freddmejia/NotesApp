package botix.gamer.notesapp.ui.menu.note

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import botix.gamer.notesapp.data.model.Note
import botix.gamer.notesapp.presentation.note.NoteViewModel
import botix.gamer.notesapp.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteScreen(
    noteViewModel: NoteViewModel,
    paddingValues: PaddingValues
) {

    val presentDialog: Boolean by noteViewModel.presentDialog.observeAsState(initial = false)

    //var openDialog = remember{ mutableStateOf(false) }
    val notes = listOf<Note>(
        Note(0,"DOCTORADO INVESTIGACION","Reuniones","24/4/2024","24/4/2024"),
        Note(0,"COMPRAR","Leche","24/4/2024","24/4/2024"),
        Note(0,"lol","videojuegos","24/4/2024","24/4/2024"),
        Note(0,"Que es ser desarrolador Senior?","Preguntar a todos","24/4/2024","24/4/2024"),
        Note(0,"Valencia playa","Reuniones","24/4/2024","24/4/2024"),
        Note(0,"Keys","Aws","24/4/2024","24/4/2024"),

        )

    if (presentDialog) {
        dialogCreateNote(noteViewModel = noteViewModel)
    }
    Box (
        modifier = Modifier.fillMaxSize()
            .padding(paddingValues)
    ) {
        Column {
            Box (
                modifier = Modifier.weight(1F)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                ) {
                    items(notes) { note ->
                        noteItem(note = note)
                    }
                }
            }

            Button(
                modifier = Modifier
                    .padding(all = 20.dp)
                    .align(Alignment.End),
                onClick = {
                    noteViewModel.handleDialogCreate(presentDialog = true)
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
}

@Composable
fun dialogCreateNote( noteViewModel: NoteViewModel) {
    val title: String by noteViewModel.title.observeAsState(initial = "")
    val text: String by noteViewModel.text.observeAsState(initial = "")
    val presentTextNote: Boolean by noteViewModel.presentTextNote.observeAsState(initial = false)

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 10.dp)
                .shadow(8.dp, shape = RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        MaterialTheme.colorScheme.surface,
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 13.dp)
                ) {
                    IconButton(
                        onClick = {
                            noteViewModel.handleDialogCreate(presentDialog = false)
                        },
                        ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.create_note),
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        modifier = Modifier
                            .align(Alignment.CenterVertically),

                        )

                    Spacer(modifier = Modifier.weight(1F))
                    TextButton(
                        onClick = {

                        },
                    ) {
                        Text(
                            text = stringResource(id = R.string.btn_ok),
                            textAlign = TextAlign.End
                        )
                    }

                }

                BasicTextField(
                    value = title,
                    onValueChange = {
                        noteViewModel.onNoteChange(
                            title = it,
                            text = text
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                )

                if (presentTextNote) {
                    BasicTextField(
                        value = text,
                        onValueChange = {
                            noteViewModel.onNoteChange(
                                title = title,
                                text = it
                            )
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Default
                        ),

                        )
                }
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
    NoteScreen(noteViewModel = NoteViewModel(), paddingValues = PaddingValues())
}