package botix.gamer.notesapp.utils

class Hosts {
    companion object {
        const val server = "http://192.168.1.143:8000/graphql"

        public fun hostServer(): String {
            return server
        }
    }
}