package botix.gamer.notesapp.presentation.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NoteViewModel: ViewModel() {

    private val _presentDialog = MutableLiveData<Boolean> (false)
    val presentDialog: LiveData<Boolean> = _presentDialog

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    private val _presentTextNote = MutableLiveData<Boolean> (false)
    val presentTextNote: LiveData<Boolean> = _presentTextNote
    fun handleDialogCreate(presentDialog: Boolean) {
        _presentDialog.value = presentDialog
    }

    fun onNoteChange(title: String, text: String) {
        _title.value = title
        _text.value = text
        if (!_title.value.isNullOrEmpty()) {
            _presentTextNote.value = true
        }
    }

    fun onNoteChangeNewLine() {
        var tempText = _text.value
        tempText = tempText + "1212-=\r\n"
        _text.value = tempText.toString()
    }
}