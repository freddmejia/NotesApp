package botix.gamer.notesapp.utils

data class CompositionObj<T, T1>(val data: T, val message: T1){
    constructor(): this(null as T, null as T1)
}