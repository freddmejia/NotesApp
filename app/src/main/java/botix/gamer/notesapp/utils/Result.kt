package botix.gamer.notesapp.utils


sealed class Result<out T> {
    data class Success<T>(val data: T): Result<T>()
    data class Error(val error: String): Result<Nothing>()
    object Empty: Result<Nothing>()
    object Loading: Result<Nothing>()
}