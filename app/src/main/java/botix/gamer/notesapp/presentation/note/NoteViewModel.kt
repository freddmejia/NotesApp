package botix.gamer.notesapp.presentation.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import botix.gamer.notesapp.data.model.Note
import botix.gamer.notesapp.data.model.User
import botix.gamer.notesapp.domain.note.NoteCreateUseCase
import botix.gamer.notesapp.domain.note.NoteUpdateUseCase
import botix.gamer.notesapp.domain.user.LoginUseCase
import botix.gamer.notesapp.domain.user.RegisterUseCase
import botix.gamer.notesapp.utils.CompositionObj
import botix.gamer.notesapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteCreateUseCase: NoteCreateUseCase,
    private val noteUpdateUseCase: NoteUpdateUseCase,
): ViewModel() {

    private val _presentDialog = MutableLiveData<Boolean> (false)
    val presentDialog: LiveData<Boolean> = _presentDialog

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> = _userId

    private val _presentTextNote = MutableLiveData<Boolean> (false)
    val presentTextNote: LiveData<Boolean> = _presentTextNote

    private val _resultNote = MutableStateFlow<Result<CompositionObj<Note, String>>>(Result.Empty)
    val resultNote: StateFlow<Result<CompositionObj<Note, String>>> = _resultNote

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun handleDialogCreate(presentDialog: Boolean) {
        _presentDialog.value = presentDialog
    }

    fun resetValues() {
        _title.value = ""
        _text.value = ""
        _resultNote.value = Result.Empty
    }
    fun onNoteChange(title: String, text: String) {
        _title.value = title
        _text.value = text
        if (!_title.value.isNullOrEmpty()) {
            _presentTextNote.value = true
        }
    }

    fun onIdUser(userId: Int) {
        _userId.value = userId
    }
    fun createNote() = viewModelScope.launch {
        _resultNote.value = Result.Empty
        _loading.value = true
        val createNoteResponse = noteCreateUseCase.execute(
            title = _title.value.toString(),
            note = _text.value.toString(),
            status = "1",
            userId = _userId.value!!.toInt()
        )

        createNoteResponse?.let {
            _resultNote.value = Result.Success(
                CompositionObj(it, "")
            )
        }?: run {
            _resultNote.value = Result.Error("")
        }

        _loading.value = false
    }

    fun onNoteChangeNewLine() {
        var tempText = _text.value
        tempText = tempText + "1212-=\r\n"
        _text.value = tempText.toString()
    }
}