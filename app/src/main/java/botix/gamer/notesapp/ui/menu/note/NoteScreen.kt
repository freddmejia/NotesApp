package botix.gamer.notesapp.ui.menu.note

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import androidx.hilt.navigation.compose.hiltViewModel
import botix.gamer.notesapp.data.model.Note
import botix.gamer.notesapp.presentation.note.NoteViewModel
import botix.gamer.notesapp.R
import botix.gamer.notesapp.utils.CompositionObj
import botix.gamer.notesapp.utils.Result
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun NoteScreen(
    noteViewModel: NoteViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {

    val presentDialog: Boolean by noteViewModel.presentDialog.observeAsState(initial = false)
    val resultNote: Result<CompositionObj<Note, String>> by noteViewModel.resultNote.collectAsState(
        initial = Result.Empty
    )
    val resultListNotes: Result<CompositionObj<ArrayList<Note>, String>> by noteViewModel.resultListNotes.collectAsState(
        initial = Result.Empty
    )
    val userId: Int by noteViewModel.userId.observeAsState(initial = 0)
    val loading: Boolean by noteViewModel.loading.observeAsState(initial = false)



    LaunchedEffect(true) {
        if (userId > 0) {
            noteViewModel.fetchNotesByUserId(
                status = "1"
            )
        }
    }

    if (resultNote is Result.Success) {
        Toast.makeText(LocalContext.current, "Note created", Toast.LENGTH_SHORT).show()
        noteViewModel.fetchNotesByUserId(
            status = "1"
        )
        noteViewModel.resetValues()
    }


    if (presentDialog) {
        DialogCreateNote(noteViewModel = noteViewModel)
    }


    Box (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                        .height(64.dp)
                        .align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
            else {
                Box(
                    modifier = Modifier.weight(1F)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(
                                bottom = 10.dp,
                                top = 20.dp,
                                start = 15.dp,
                                end = 15.dp
                            )
                    ) {
                        if (resultListNotes is Result.Success) {
                            items(
                                (resultListNotes as Result.Success<CompositionObj<ArrayList<Note>, String>>).data.data,
                                key = {
                                    it.id
                                }
                            ) { note ->
                                SwipeToDeleteContainer(
                                    item = note,
                                    onDelete = {
                                        noteViewModel.deleteNoById(note = note)
                                    }

                                ){
                                    NoteItem(
                                        note = it,
                                        fetchNoteById = { noteId: Int ->
                                            noteViewModel.fetchLocalListNoteById(noteId = noteId)
                                        }
                                    )
                                }

                            }
                        } else {
                            item {
                                Image(
                                    painter = painterResource(id = R.drawable.placeholder_nodata),
                                    contentDescription = "header",
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.padding(
                                        bottom = 20.dp
                                    )
                                )
                            }
                            item {
                                Text(
                                    text = stringResource(id = R.string.no_data),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    modifier = Modifier.padding(
                                        start = 20.dp
                                    )
                                )
                            }
                        }
                    }
                }

                Button(
                    modifier = Modifier
                        .padding(all = 20.dp)
                        .align(Alignment.End),
                    onClick = {
                        noteViewModel.resetValues()
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
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                isRemoved = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if(isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {

        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                DeleteBackground()
            },
            content = {
                content(item)
            },
            enableDismissFromStartToEnd = true
        )
    }
}

@Composable
fun DeleteBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 10.dp)
            .shadow(8.dp, shape = RoundedCornerShape(16.dp))
            .background(Color.Red),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Composable
fun DialogCreateNote(noteViewModel: NoteViewModel) {
    val title: String by noteViewModel.title.observeAsState(initial = "")
    val text: String by noteViewModel.text.observeAsState(initial = "")
    val presentTextNote: Boolean by noteViewModel.presentTextNote.observeAsState(initial = false)
    val canSaveNote: Boolean by noteViewModel.canSaveNote.observeAsState(initial = false)
    val resultNoteRecover: Note? by noteViewModel.resultNoteRecover.collectAsState(
        initial = null
    )

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
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
                    if (canSaveNote) {
                        TextButton(
                            onClick = {
                                noteViewModel.handleDialogCreate(presentDialog = false)
                                if(resultNoteRecover != null) {
                                    noteViewModel.updateNote()
                                }
                                else {
                                    noteViewModel.createNote()
                                }
                            },
                        ) {
                            Text(
                                text = stringResource(id = R.string.btn_ok),
                                textAlign = TextAlign.End
                            )
                        }
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
fun NoteItem(note : Note, fetchNoteById: (noteId: Int) -> Unit) {
    OutlinedCard (
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 5.dp,
                vertical = 5.dp
            ),
        onClick = {
            fetchNoteById( note.id )
            Log.e("CLOCKE","CLICKED")
        }

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
fun PreviewL() {
    NoteScreen( paddingValues = PaddingValues())
}