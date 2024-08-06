package botix.gamer.notesapp.presentation.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import botix.gamer.notesapp.data.model.Note
import botix.gamer.notesapp.di.TokenManager
import botix.gamer.notesapp.domain.note.FetchNoteByUserUseCase
import botix.gamer.notesapp.domain.note.NoteCreateUseCase
import botix.gamer.notesapp.domain.note.NoteUpdateUseCase
import botix.gamer.notesapp.utils.CompositionObj
import botix.gamer.notesapp.utils.Result
import botix.gamer.notesapp.utils.Utility
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteCreateUseCase: NoteCreateUseCase,
    private val noteUpdateUseCase: NoteUpdateUseCase,
    private val fetchNoteByUserUseCase: FetchNoteByUserUseCase,
    tokenManager: TokenManager
): ViewModel() {

    private val _presentDialog = MutableLiveData (false)
    val presentDialog: LiveData<Boolean> = _presentDialog

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> = _userId

    private val _presentTextNote = MutableLiveData (false)
    val presentTextNote: LiveData<Boolean> = _presentTextNote

    private val _resultNote = MutableStateFlow<Result<CompositionObj<Note, String>>>(Result.Empty)
    val resultNote: StateFlow<Result<CompositionObj<Note, String>>> = _resultNote

    private val _resultListNotes = MutableStateFlow<Result<CompositionObj<ArrayList<Note>, String>>>(Result.Empty)
    val resultListNotes: StateFlow<Result<CompositionObj<ArrayList<Note>, String>>> = _resultListNotes

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _canSaveNote = MutableLiveData<Boolean>()
    val canSaveNote: LiveData<Boolean> = _canSaveNote

    private val _resultNoteRecover = MutableStateFlow<Note?>(null)
    val resultNoteRecover: StateFlow<Note?> = _resultNoteRecover

    private val _listNotes = MutableStateFlow<ArrayList<Note>>(arrayListOf())
    val listNotes: StateFlow<ArrayList<Note>> = _listNotes

    init {
        _resultListNotes.value = Result.Empty
        _listNotes.value = arrayListOf()
        val user = tokenManager.fetchLocalUser()
        user?.let {
            onIdUser(userId = user.id)
        }
    }
    fun handleDialogCreate(presentDialog: Boolean) {
        _presentDialog.value = presentDialog
    }

    fun resetValues() {
        //if (_resultNoteRecover.value != null) {
            _title.value = ""
            _text.value = ""
            _resultNoteRecover.value = null
            _presentTextNote.value = false
            _canSaveNote.value = false
       // }
        _resultNote.value = Result.Empty
    }

    fun onNoteChange(title: String, text: String) {
        _title.value = title
        _text.value = text
        val titleTemp = _title.value.toString()
        if (titleTemp.replace("\\s".toRegex(), "").isNotEmpty()) {
            _presentTextNote.value = true
        }

        val textTemp = _text.value.toString()
        _canSaveNote.value = titleTemp.replace("\\s".toRegex(), "").isNotEmpty() &&
                textTemp.replace("\\s".toRegex(), "").isNotEmpty()

    }

    private fun onIdUser(userId: Int) {
        _userId.value = userId
    }

    fun createNote() = viewModelScope.launch {
        var newNoteObj = Note(
            id = 0,
            title = _title.value.toString(),
            note = _text.value.toString(),
            createdAt = Utility.getCurrentDateTimeUtc(Utility.format),
            updatedAt = "",
            status = Utility.statusActive(),
        )

        val createNoteResponse = noteCreateUseCase.executeLocal(
            note = newNoteObj
        )

        createNoteResponse?.let {  noteCreateLocal ->
            newNoteObj.id = noteCreateLocal.id
            addNewNoteToList(
                note = newNoteObj
            )
            handleDialogCreate(presentDialog = false)

            //createNoteOnApi(note = newNoteCreated)
            val createNoteResponseApi = noteCreateUseCase.execute(
                note = newNoteObj,
                userId = _userId.value!!.toInt(),
            )

            createNoteResponseApi?.let { noteCreateApi->
                newNoteObj.id = noteCreateApi.id
                noteUpdateUseCase.executeUpdate(note = newNoteObj)
                updateListNote(note = newNoteObj)
            }
        }
    }
    private fun addNewNoteToList(note: Note) = viewModelScope.launch {
        val noteTemp = note.copy()
        var tempList = _listNotes.value
        noteTemp.createdAt = Utility.parseDateTimeUtcToLocalTime(dateTimeUtc = noteTemp.createdAt, format = Utility.format)
        tempList.add(0, noteTemp)

        _listNotes.value = tempList

        _resultListNotes.value  = Result.Success(
            CompositionObj(tempList, "")
        )
    }
    private fun updateListNote(note: Note) = viewModelScope.launch {
        var noteTemp = note.copy()          //UTC
        var tempList = _listNotes.value     //LOCALTIME

        noteTemp.createdAt = Utility.parseDateTimeUtcToLocalTime(dateTimeUtc = noteTemp.createdAt, format = Utility.format)

        if (tempList.isNotEmpty()) {
            tempList.mapIndexed{ index, item ->
                if (noteTemp.createdAt.equals(item.createdAt)) {
                    tempList.set(index = index, noteTemp)
                }
            }
        }

        _listNotes.value = tempList

        _resultListNotes.value  = Result.Success(
            CompositionObj(tempList, "")
        )
    }
    fun updateNote() = viewModelScope.launch {
        var noteObj = Note(
            id = _resultNoteRecover.value!!.id,
            title = _title.value.toString(),
            note = _text.value.toString(),
            createdAt = Utility.parseDateTimeLocalToUtc(dateTimeLocal = _resultNoteRecover.value!!.createdAt, format = Utility.format)  ,//Utility.getCurrentDateTimeUtc(Utility.format),
            updatedAt = _resultNoteRecover.value!!.updatedAt,
            status = Utility.statusActive(),
        )

        val noteUpdated = noteUpdateUseCase.executeUpdate(note = noteObj)
        noteUpdated?.let { noteUpdt->
            handleDialogCreate(presentDialog = false)

            updateListNote(note = noteObj)
            noteUpdateUseCase.execute(
                title = noteUpdt.title,
                note = noteUpdt.note,
                status = noteUpdt.status,
                idNote = noteUpdt.id,
                createdAt = noteUpdt.createdAt
            )

        }

    }

    fun fetchNotesByUserId(status: String) = viewModelScope.launch {
        _loading.value = true
        _listNotes.value = arrayListOf()
        _resultListNotes.value = Result.Empty
        var fetchLocalListNotes = fetchNoteByUserUseCase.executeFetchNotesLocal()

        if (fetchLocalListNotes.isNotEmpty()) {
            fetchLocalListNotes.forEach { note ->
                addNewNoteToList(note = note)
            }
        }
        //else {
            val fetchNotesResponse = fetchNoteByUserUseCase.execute(
                status = status,
                userId = _userId.value!!.toInt()
            )

            fetchNotesResponse.let { listNotes->

                if (listNotes.isNotEmpty()) {
                    listNotes.forEach { note ->

                        val existNoteLocal = fetchLocalListNotes.filter { it.createdAt.equals(note.createdAt) }

                        if (existNoteLocal.isEmpty()){
                            addNewNoteToList(note = note)
                            noteCreateUseCase.executeLocal(note = note)
                        }
                    }
                }
                else {
                    _resultListNotes.value = Result.Error("")
                }
            }
        //}
        _loading.value = false

    }

    fun fetchLocalListNoteById(noteId: Int) {
        _resultNoteRecover.value = null
        val listTemp = _resultListNotes.value
        if (listTemp is Result.Success){
            listTemp.data.data.map { noteItem->
                if (noteItem.id == noteId) {
                    _resultNoteRecover.value = noteItem
                    onNoteChange(
                        title = noteItem.title,
                        text = noteItem.note
                    )
                    handleDialogCreate(
                        presentDialog = true
                    )
                    return@map
                }
            }
        }
    }

    fun deleteNoById(note: Note) = viewModelScope.launch {

        noteUpdateUseCase.executeDeleteNote(note = note)
        val resulUpdateUseCase = noteUpdateUseCase.execute(
            idNote = note.id,
            title = note.title,
            note = note.note,
            status = "0",
            createdAt = Utility.parseDateTimeLocalToUtc(dateTimeLocal = note.createdAt, format = Utility.format)
        )
        resulUpdateUseCase?.let {
            fetchNotesByUserId(Utility.statusActive())
        }
    }

}